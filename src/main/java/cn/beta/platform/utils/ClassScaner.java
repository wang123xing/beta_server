package cn.beta.platform.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description  扫描Bean
 */
@Slf4j
public class ClassScaner implements ResourceLoaderAware {
    //保存过滤规则包含的注解
    private final List<TypeFilter> includeFilters = new LinkedList<TypeFilter>();
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
    public static Set<Class> scan(String[] basePackages,
                                  Class<? extends Annotation>... annotations) {
        ClassScaner cs = new ClassScaner();
        Optional.ofNullable(annotations).ifPresent(x->{
            Arrays.stream(x).forEach(c->cs.addIncludeFilter(new AnnotationTypeFilter(c)));
        });
        return Optional.ofNullable(basePackages).map(x-> Arrays.stream(x).map(c->cs.doScan(c)).collect(() -> new HashSet<Class>(),
                (set ,p) ->set.addAll(p),(m ,n) -> m.addAll(n))).orElseGet(HashSet::new);
    }

    public static Set<Class> scan(String basePackages, Class<? extends Annotation>... annotations) {
        return ClassScaner.scan(StringUtils.tokenizeToStringArray(basePackages, ",; \t\n"), annotations);
    }
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils
                .getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(
                resourceLoader);
    }

    public void addIncludeFilter(TypeFilter includeFilter) {
        this.includeFilters.add(includeFilter);
    }
    private Set<Class> doScan(String basePackage) {
        Set<Class> classes = new HashSet<Class>();
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX  + org.springframework.util.ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/**/*.class";
            classes=Arrays.stream(this.resourcePatternResolver.getResources(packageSearchPath)).filter(Resource::isReadable).map(x->{
                try {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(x);
                    if ((includeFilters.size() == 0 ) || matches(metadataReader)) {
                        return Class.forName(metadataReader.getClassMetadata().getClassName());
                    }
                } catch (ClassNotFoundException | IOException e) {
                    log.error("ClassScaner Error is {}",e.getMessage(),e);
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toSet());
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return classes;
    }

    private boolean matches(MetadataReader metadataReader) {
        return this.includeFilters.stream().map(x-> {
            try {
                return x.match(metadataReader, this.metadataReaderFactory);
            } catch (IOException e) {
                log.error("message : {}",e.getMessage());
            }
            return false;
        }).findFirst().orElse(false);
    }

    public  void main(String[] args) {
        ClassScaner.scan("com.guazi.sale.carservice.app.tasks", Service.class)
                .forEach(clazz -> System.out.println(clazz));
    }
}

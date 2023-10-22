package cn.beta.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分销基础专题信息
 * </p>
 *
 * @author beta
 * @since 2023-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DistributionTopicInfo对象", description="分销基础专题信息")
public class DistributionTopicInfo extends Model<DistributionTopicInfo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "专题id")
    private Long topicId;

    @ApiModelProperty(value = "专题名称")
    private String topicName;

    @ApiModelProperty(value = "专题别名")
    private String topicAlias;

    @ApiModelProperty(value = "专题封面图")
    private String coverImageUrl;

    @ApiModelProperty(value = "类别")
    private String categories;

    @ApiModelProperty(value = "专题描述")
    private String description;

    @ApiModelProperty(value = "价格")
    private Long price;

    @ApiModelProperty(value = "ios价格")
    private Long iosPrice;

    @ApiModelProperty(value = "付费点")
    private Long startComicNumber;

    @ApiModelProperty(value = "更新状态 0停更 1连载中 2 已完结")
    private Integer updateStatus;

    @ApiModelProperty(value = "上架时间")
    private Long publishedTime;

    @ApiModelProperty(value = "作品状态 1 上架 0待上架")
    private Long status;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    private Long updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

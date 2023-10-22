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
 * 推广链接
 * </p>
 *
 * @author beta
 * @since 2023-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="LinkInfo对象", description="推广链接")
public class LinkInfo extends Model<LinkInfo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "营销商id")
    private Long marketerId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "漫画id")
    private Long topicId;

    @ApiModelProperty(value = "章节id")
    private Long chapterId;

    @ApiModelProperty(value = "投放媒体，见MediaType")
    private Integer media;

    @ApiModelProperty(value = "h5链接")
    private String h5Link;

    @ApiModelProperty(value = "小程序链接")
    private String miniLink;

    @ApiModelProperty(value = "回传规则id")
    private Long adCallbackRuleId;

    @ApiModelProperty(value = "充值模版id")
    private Long rechargeTemplateId;

    @ApiModelProperty(value = "状态，1-生效；0-未生效")
    private Integer status;

    @ApiModelProperty(value = "日期")
    private Integer curDate;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    private Long updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

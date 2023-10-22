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
 * 营销商信息表
 * </p>
 *
 * @author beta
 * @since 2023-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MarketerInfo对象", description="营销商信息表")
public class MarketerInfo extends Model<MarketerInfo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色")
    private Integer role;

    @ApiModelProperty(value = "父账号id")
    private Long parentId;

    @ApiModelProperty(value = "小程序id")
    private Long appId;

    @ApiModelProperty(value = "营销商名称")
    private String marketerName;

    @ApiModelProperty(value = "营销商备注")
    private String description;

    @ApiModelProperty(value = "状态")
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

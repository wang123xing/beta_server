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
 * 营销商账户表
 * </p>
 *
 * @author beta
 * @since 2023-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MarketerAccount对象", description="营销商账户表")
public class MarketerAccount extends Model<MarketerAccount> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "营销商id")
    private Long marketerId;

    @ApiModelProperty(value = "用户名")
    private String identifier;

    @ApiModelProperty(value = "密码，md5加密")
    private String password;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "负责人")
    private String manager;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    private Long updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

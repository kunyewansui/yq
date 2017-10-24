/**
 * Created by GustinLau on 2017-05-03.
 */

jQuery.validator.addMethod("notEmpty", function (value, element) {
        return this.optional(element) || /\S+\s*$/.test(value);
    }, $.validator.format("不能为空")
);

jQuery.validator.addMethod("mobile", function (value, element) {
        return this.optional(element) || /^1[34578]\d{9}$/.test(value);
    }, $.validator.format("手机格式错误")
);
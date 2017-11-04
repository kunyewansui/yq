/**
 * Created by GustinLau on 2017-04-05.
 */
$(function () {
    //Nav
    var c_href = window.location.href;
    var nav = $("#a_nav");
    var navLinks = nav.find("a");
    var haveActive = false;

    nav.find("a").each(function () {
        var a_href = $(this).context.href.trim();
        if (a_href !== "" && a_href !== $(this).context.origin + "/" && c_href.indexOf(a_href) === 0) {
            var nexStr = c_href.substr(a_href.length, 1);
            if (nexStr === "" || nexStr === "?" || nexStr === "#" || nexStr === "/") {
                $(this).parent("li").addClass("active");
                haveActive = true;
                return false;
            }
        }
    });

    if (!haveActive && navLinks.length > 0) {
        navLinks[0].click();
    }

    nav.find("li").each(function () {
        var $this = $(this);
        if ($this.has("ul").length > 0) {
            var a = $($this.find("a")[0]);
            a.addClass("auto");
            a.prepend(
                "<span class='pull-right text-muted'>" +
                "<i class='fa fa-fw fa-angle-right text' style='line-height: 20px'></i>" +
                "<i class='fa fa-fw fa-angle-down text-active' style='line-height: 20px'></i>" +
                "</span>"
            );

            a.on('click', function (event) {
                event.preventDefault();
                $("a.auto").parent("li").removeClass("active");
                $(this).parent("li").addClass("active");
            });

            $this.find("li.active").each(function () {
                $(this).parent("ul").siblings("a.auto").parent("li").addClass("active");
            });
        }
    });

    var tNav = $("#t_nav");
    tNav.find("a").each(function () {
        var a_href = $(this).context.href.trim();
        if (a_href !== "" && a_href !== $(this).context.origin + "/" && c_href.indexOf(a_href) === 0) {
            var nexStr = c_href.substr(a_href.length, 1);
            if (nexStr === "" || nexStr === "?" || nexStr === "#" || nexStr === "/")
                $(this).addClass("active");
        }
    });

    $("*[data-toggle-class]").on("click", function () {
        var $this = $(this);
        var classes = $this.data('toggle-class').split(','),
            target = $this.data('toggle-target');

        $.each(classes, function (index, _class) {
            $(target).toggleClass(_class);
        });

        $this.toggleClass('active');

    });


    var dropdownOpened = false;
    $(".dropdown-toggle").on("click", function () {
        $(this).parents(".dropdown").toggleClass("open");
        setTimeout(function () {
            dropdownOpened = $(".dropdown").hasClass("open");
        }, 600)
    });
    $(document).on("click", function (e) {
        if ($(e.target).closest('.dropdown-menu').length === 0) {
            if (dropdownOpened) {
                $(".dropdown").removeClass("open");
                dropdownOpened = false;
            }
        }
    });

});

(function ($) {
    $.fn.xsEnable = function () {
        var inputs = this.find("input");
        var selects = this.find("select");
        var textareas = this.find("textarea");
        var buttons = this.find("button");
        var els = [];
        $.each(inputs, function (index, element) {
            els.push($(element));
        });
        $.each(selects, function (index, element) {
            els.push($(element));
        });
        $.each(textareas, function (index, element) {
            els.push($(element));
        });
        $.each(buttons, function (index, element) {
            els.push($(element));
        });
        $.each(els, function (index, element) {
            element.attr('disabled', false);
        });
    };
    $.fn.xsDisable = function () {
        var inputs = this.find("input");
        var selects = this.find("select");
        var textareas = this.find("textarea");
        var buttons = this.find("button");
        var els = [];
        $.each(inputs, function (index, element) {
            els.push($(element));
        });
        $.each(selects, function (index, element) {
            els.push($(element));
        });
        $.each(textareas, function (index, element) {
            els.push($(element));
        });
        $.each(buttons, function (index, element) {
            els.push($(element));
        });
        $.each(els, function (index, element) {
            element.attr('disabled', true);
        });
    };
    $.fn.xsClean = function () {
        var inputs = this.find("input");
        var selects = this.find("select");
        var textareas = this.find("textarea");
        var els = [];
        $.each(inputs, function (index, element) {
            if ($(element).attr('type') === 'button' || $(element).attr('type') === 'submit' || $(element).data('ignore'))
                return true;
            if ($(element).data('value')) {
                $(element).val($(element).data('value'));
                return true;
            }
            els.push($(element));
        });
        $.each(selects, function (index, element) {
            els.push($(element));
        });
        $.each(textareas, function (index, element) {
            els.push($(element));
        });
        $.each(els, function (index, element) {
            element.val("");
        });
    };
    $.fn.xsSetInput = function (name, val) {
        $(this.find("[name='" + name + "']")[0]).val(val);
    };
    $.fn.xsGetInput = function (name) {
        return $(this.find("[name='" + name + "']")[0]).val();
    };
    $.fn.xsSetForm = function (json) {
        var jsonObject;
        var _this = this;
        var setSub = function (sk, obj) {
            var keys = sk.split('.');
            for (var k2 in obj) {
                if (obj[k2] instanceof Object) {
                    setSub(keys.concat(k2).join('.'), obj[k2]);
                } else {
                    _this.xsSetInput(keys.join('.') + "." + k2, obj[k2]);
                }
            }
        };
        if (json instanceof String) {
            jsonObject = JSON.parse(json);
        } else {
            jsonObject = json;
        }
        for (var k in jsonObject) {
            if (jsonObject.hasOwnProperty(k)) {
                if (jsonObject[k] instanceof Object) {
                    setSub(k, jsonObject[k]);
                } else {
                    this.xsSetInput(k, jsonObject[k]);
                }
            }
        }
    };
    $.fn.xs = function (name, value) {
        if (value === undefined) {
            return this.attr("xs-" + name);
        } else {
            this.attr("xs-" + name, value);
        }
    };
    $.fn.xsDataValidate = function (name, value) {
        if (value === undefined) {
            return this.attr("validate-" + name);
        } else {
            this.attr("validate-" + name, value);
        }
    };
    $.fn.xsValidate = function (submitHandler) {
        var inputs = this.find("input");
        var selects = this.find("select");
        var textareas = this.find("textarea");

        function setRulesAndMsg($element) {
            if ($element.attr('type') === 'button' || $element.attr('type') === 'submit' || $element.data('ignore'))
                return;
            var attrs = $element[0].attributes;
            var rule = {};
            var message = {};
            for (var i = 0; i < attrs.length; i++) {
                if (attrs.item(i).name.indexOf('validate-') === 0) {
                    var ruleName = _validateSensitiveCaseChange(attrs.item(i).name.split('-')[1]);
                    if ($element.xsDataValidate(ruleName) !== undefined && $element.xsDataValidate(ruleName) !== "") {
                        var r = $element.xsDataValidate(ruleName);
                        var r2 = r.split("\|");
                        if (r2.length > 1) {
                            if (r2[0] === 'true')
                                rule[ruleName] = true;
                            else
                                rule[ruleName] = r2[0];
                            message[ruleName] = r2[1]
                        }
                    }
                }
            }
            rules[$element.attr("name")] = rule;
            messages[$element.attr("name")] = message;
        }

        var rules = {};
        var messages = {};
        $.each(inputs, function (index, element) {
            setRulesAndMsg($(element));
        });
        $.each(selects, function (index, element) {
            setRulesAndMsg($(element));
        });
        $.each(textareas, function (index, element) {
            setRulesAndMsg($(element));
        });


        var validator = this.validate({
            rules: rules,
            messages: messages,
            submitHandler: submitHandler
        });

        console.log(validator);
        return validator;
    }
})(jQuery);

$(function () {
    $('select[data-value]').each(function (i, e) {
        var $e = $(e);
        $e.val($e.data('value'));
    })
});

var __oldRequestDataMap__ = {};

function _isNewRequestData(url, _new) {
    var newStr, isNew;
    if (_new instanceof Object) {
        newStr = JSON.stringify(_new);
    } else {
        newStr = _new;
    }
    if (__oldRequestDataMap__.hasOwnProperty(url)) {
        var oldStr = __oldRequestDataMap__[url];
        isNew = newStr !== oldStr;
        if (isNew) {
            __oldRequestDataMap__[url] = newStr;
        }
        return isNew;
    } else {
        __oldRequestDataMap__[url] = newStr;
        return true;
    }
}


function _ajaxRequest(url, method, data, success, error) {
    setTimeout(function () {
        var time = setTimeout(function () {
            showLoadingView()
        }, 800);
        if (error === undefined || error === null) {
            error = function (XMLHttpRequest, textStatus) {
                alert("请求失败：" + textStatus + "\n错误码：" + XMLHttpRequest.status);
            }
        }
        $.ajax({
            type: method,
            url: url,
            data: data,
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Accept', 'application/json');
            },//这里设置header
            success: success,
            error: error,
            complete: function () {
                clearTimeout(time);
                hideLoadingView();
                delete __oldRequestDataMap__[url];
            }
        });
    }, 1000);
}

function _doPostEvent(url, data, success, error) {
    _ajaxRequest(url, "POST", data, success, error);
}

function _doGetEvent(url, data, success, error) {
    _ajaxRequest(url, "GET", data, success, error);
}

function doPost(url, data, success, error) {
    if (_isNewRequestData(url, data)) {
        _doPostEvent(url, data, success, error, true);
    }
}

function doGet(url, data, success, error) {
    if (_isNewRequestData(url, data)) {
        _doGetEvent(url, data, success, error);
    }
}

function uploadFile(url, formData, success, error) {
    if (error === undefined || error === null) {
        error = function (res) {
            alert("请求失败：" + res.statusText + "\n错误码：" + res.status);
        }
    }
    $.ajax({
        type: 'POST',
        url: url,
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        dataType: 'json'
    }).done(success).fail(error);
}

function showLoadingView() {
    hideLoadingView();
    var xsLoadingView = $('<div>', {"id": "xsLoadingView", "class": "xs-loading"});
    xsLoadingView.append($('<i>', {"class": 'fa fa-spinner fa-pulse'}));
    $('body').append(xsLoadingView);
}

function hideLoadingView() {
    $("#xsLoadingView").remove();
}

function imageUpload(url, folder, file, success, error) {
    var formData = new FormData();
    formData.append("folders", folder);
    formData.append("files", file);
    uploadFile(url, formData, success, error);
}

function putImageIntoImageUploader(id, url) {
    eval('images_' + id + '.splice(0,images_' + id + '.length)');
    eval('images_' + id + '.push("' + url + '")');
    eval('$url_' + id + '.val("' + url + '")');
    eval('updatePreviewDiv_' + id + '()');
}
function cleanImageInUploader(id) {
    eval('images_' + id + '.splice(0,images_' + id + '.length)');
    eval('updatePreviewDiv_' + id + '()');
}

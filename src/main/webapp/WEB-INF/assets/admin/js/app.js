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
            for (var k2 in obj) {
                if (obj.hasOwnProperty(k2)) {
                    if (obj[k2] instanceof Object) {
                        setSub(sk+"."+k2, obj[k2]);
                    } else {
                        _this.xsSetInput(sk + "." + k2, obj[k2]);
                    }
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
                    setSub(k,jsonObject[k]);
                } else {
                    this.xsSetInput(k, jsonObject[k]);
                }

            }

        }
    }
})(jQuery);

$(function () {
    $('select[data-value]').each(function (i, e) {
        var $e = $(e);
        $e.val($e.data('value'));
    })
});

function doPost(url, data, success, error) {
    var time = setTimeout(function () {
        showLoadingView()
    },800);
    var e,s;
    if (error === undefined || error === null) {
        e = function (XMLHttpRequest, textStatus) {
            clearTimeout(time);
            hideLoadingView();
            alert("请求失败：" + textStatus + "\n错误码：" + XMLHttpRequest.status);
        }
    } else {
        e = function (XMLHttpRequest, textStatus) {
            clearTimeout(time);
            hideLoadingView();
            error(XMLHttpRequest, textStatus);
        }
    }
    s = function (data) {
        clearTimeout(time);
        hideLoadingView();
        success(data);
    };
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        dataType: 'json',
        success: s,
        error: e
    })
}

function doGet(url, data, success, error) {
    var time=setTimeout(function () {
        showLoadingView()
    },800);
    var e,s;
    if (error === undefined || error === null) {
        e = function (XMLHttpRequest, textStatus) {
            clearTimeout(time);
            hideLoadingView();
            alert("请求失败：" + textStatus + "\n错误码：" + XMLHttpRequest.status);
        }
    }else{
        e=function (XMLHttpRequest, textStatus) {
            clearTimeout(time);
            hideLoadingView();
            error(XMLHttpRequest, textStatus);
        }
    }
    s=function (data) {
        clearTimeout(time);
        hideLoadingView();
        success(data);
    };
    $.ajax({
        type: 'GET',
        url: url,
        data: data,
        dataType: 'json',
        success: s,
        error: e
    })
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
    var __xsLoadingView__ = $('<div>', {id: "xsLoadingView", class: "xs-loading"});
    __xsLoadingView__.append($('<i>', {class: 'fa fa-spinner fa-pulse'}));
    $('body').append(__xsLoadingView__);
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




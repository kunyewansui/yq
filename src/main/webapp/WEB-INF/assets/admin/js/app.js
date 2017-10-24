/**
 * Created by GustinLau on 2017-04-05.
 */

$(function () {
    //Nav
    var c_href = window.location.href;
    var nav = $("#a_nav");
    nav.find("a").each(function () {
        var a_href = $(this).context.href.trim();
        if (a_href !== "" && a_href !== $(this).context.origin + "/" && c_href.indexOf(a_href) === 0) {
            var nexStr = c_href.substr(a_href.length, 1);
            if (nexStr === "" || nexStr === "?" || nexStr === "#"|| nexStr === "/")
                $(this).parent("li").addClass("active");
        }
    });

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


function doPost(url, data, success, error) {
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        dataType: 'json',
        success: success,
        error: error
    })
}

function doPost(url, data, success) {
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        dataType: 'json',
        success: success,
        error: function (XMLHttpRequest, textStatus) {
            alert("请求失败：" + textStatus + "\n错误码：" + XMLHttpRequest.status);
        }
    })
}

function uploadFile(url, formData, success, error) {
    $.ajax({
        type: 'POST',
        url: url,
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        dataType: 'json',
    }).done(success).fail(error);
}

function uploadFile(url, formData, success) {
    $.ajax({
        type: 'POST',
        url: url,
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        dataType: 'json',
    }).done(success).fail(function (res) {
        alert("请求失败：" + res.statusText + "\n错误码：" + res.status);
    });
}

function doGet(url, data, success, error) {
    $.ajax({
        type: 'GET',
        url: url,
        data: data,
        dataType: 'json',
        success: success,
        error: error
    })
}


!
    function(t, a, e, i) {
        var n = function(a, e) {
            this.ele = a,
            this.defaults = {
                currentPage: 1,//当前页数
                totalPage: 10,//总页数
                isShow: !0,//是否显示首尾页
                isTogo: !0,//是否显示跳转
                count: 9,//显示个数
                homePageText: "<i class=\"xs-pagination-icon xs-pagination-double-left\"></i>",//首页文本
                endPageText: "<i class=\"xs-pagination-icon xs-pagination-double-right\"></i>",//尾页文本
                prevPageText: "<i class=\"xs-pagination-icon xs-pagination-left\"></i>",//上一页文本
                nextPageText: "<i class=\"xs-pagination-icon xs-pagination-right\"></i>",//下一页文本
                callback: function() {}
            },
            this.opts = t.extend({}, this.defaults, e),
            this.current = this.opts.currentPage,
            this.total = this.opts.totalPage,
            this.init()
        };
        n.prototype = {
            init: function() {
                this.render(),
                this.eventBind()
            },
            render: function() {
                var t = this.opts,
                    a = this.current,
                    e = this.total,
                    i = this.getPagesTpl(),
                    n = this.ele.empty();
                this.isRender = !0,
                this.homePage = '<li class="xs-f xs-icon"><a class="ui-pagination-page-item" data-current="1" title="第一页">' + t.homePageText + "</a></li>",
                this.prevPage = '<li class="xs-f xs-icon"><a class="ui-pagination-page-item" data-current="' + (a - 1) + '" title="上一页">' + t.prevPageText + "</a></li>",
                this.nextPage = '<li class="xs-f xs-icon"><a class="ui-pagination-page-item" data-current="' + (a + 1) + '" title="下一页">' + t.nextPageText + "</a></li>",
                this.endPage = '<li class="xs-f xs-icon"><a class="ui-pagination-page-item" data-current="' + e + '" title="最后一页">' + t.endPageText + "</a></li>",
                this.totalPage = '<span class="pagination-total-count">&nbsp;'+e+'&nbsp;</span>',
                this.extraPage = '<span class="pagination-change"></span>' +
                                 '<input id="xsPage" type="number" min="1" step="1" max="'+e+'">' +
                                 '<span class="pagination-change-page"></span>' +
                                 '<a class="ui-pagination-page-submit submit">确定</a>',
                this.checkPage(),
                this.isRender && n.html("<ul>" + this.homePage + this.prevPage + i + this.nextPage + this.endPage + "</ul><div class=\"pagination-extra\">"+this.totalPage+this.extraPage+"</div>")
            },
            checkPage: function() {
                var t = this.opts,
                    a = this.total,
                    e = this.current;
                t.isShow || (this.homePage = this.endPage = ""),
                t.isTogo || (this.extraPage = ""),
                1 === e && (this.homePage=this.homePage.replace("xs-f","disabled"), this.prevPage=this.prevPage.replace("xs-f", "disabled")),
                e === a && (this.endPage=this.endPage.replace("xs-f","disabled"), this.nextPage=this.nextPage.replace("xs-f","disabled")),
                1 === a && (this.homePage=this.homePage.replace("xs-f","disabled"), this.prevPage=this.prevPage.replace("xs-f", "disabled"),this.endPage=this.endPage.replace("xs-f","disabled"), this.nextPage=this.nextPage.replace("xs-f","disabled")),
                a <= 1 && (this.isRender = !1)
            },
            getPagesTpl: function() {
                var t = this.opts,
                    a = this.total,
                    e = this.current,
                    i = "",
                    n = t.count;
                if (a <= n) for (g = 1; g <= a; g++) i += g === e ? '<li class="active"><a class="ui-pagination-page-item" data-current="' + g + '">' + g + "</a></li>": '<li><a class="ui-pagination-page-item" data-current="' + g + '">' + g + "</a></li>";
                else {
                    var s = n / 2;
                    if (e <= s) for (g = 1; g <= n; g++) i += g === e ? '<li class="active"><a class="ui-pagination-page-item" data-current="' + g + '">' + g + "</a></li>": '<li><a class="ui-pagination-page-item" data-current="' + g + '">' + g + "</a></li>";
                    else {
                        var r = Math.floor(s),
                            h = e + r,
                            o = e - r,
                            c = n % 2 == 0;
                        h > a && (c ? (o -= h - a - 1, h = a + 1) : (o -= h - a, h = a)),
                        c || h++;
                        for (var g = o; g < h; g++) i += g === e ? '<li class="active"><a class="ui-pagination-page-item active" data-current="' + g + '">' + g + "</a></li>": '<li><a class="ui-pagination-page-item" data-current="' + g + '">' + g + "</a></li>"
                    }
                }
                return i
            },
            setPage: function(t, a) {
                return t === this.current && a === this.total ? this.ele: (this.current = t, this.total = a, this.render(), this.ele)
            },
            getPage: function() {
                return {
                    current: this.current,
                    total: this.total
                }
            },
            eventBind: function() {
                var a = this,
                    e = this.opts.callback;
                this.ele.off("click").on("click", ".ui-pagination-page-item",
                    function() {
                        var i = t(this).data("current");
                        i > 0 && i <= a.total && a.current != i && (a.current = i, a.render(), e && "function" == typeof e && e(i))
                    })

                this.ele.on("click", ".ui-pagination-page-submit",
                    function() {
                        var i = Number(t("#xsPage").val());
                        i > 0 && i <= a.total && a.current != i && (a.current = i, a.render(), e && "function" == typeof e && e(i))
                    })

            },
            trigger: function () {
                var a = this,
                    e = this.opts.callback;
                 e && "function" == typeof e && e(1)
            }
        },
        t.fn.pagination = function(t, a, e) {
            if ("object" == typeof t) {
                var i = new n(this, t);
                this.data("pagination", i)
            }
            return "string" == typeof t ? this.data("pagination")[t](a, e) : this
        }
    } (jQuery, window, document);

    /**
     * 渲染列表数据
     * @param {String} ele 列表标识
     * @param {Array} data 数据
     * @param {String} temp 列表项模板标识
     * @param {Object} opt （可选）列表项处理配置 {itemName:function(itemVal){...;return realVal}}
     */
    function renderData(ele, data, temp, opt) {
        var template = $(temp).html();
        var attr = getTemplateParams(template);
        var html = "";
        if(typeof data === 'undefined' || data.length==0){
            var cols = $(ele+" thead tr th").length;
            html = "<tr><td class='text-muted' colspan='"+cols+"'>暂无数据</td></tr>";
        }else {
            $.each(data, function (i, item) {
                var itemData = {};
                $.each(attr, function (j, ite) {
                    //解析key
                    var s = ite.split(".");
                    var v;
                    if(s.length==1){
                        v = item[ite];
                    }else{
                        var str = "item";
                        for (var g = 0; g < s.length; g++){
                            str += "['"+s[g]+"']";
                        }
                        v = eval(str);//可执行代码
                    }
                    typeof opt !== 'undefined' && opt[ite] && "function" == typeof opt[ite] ? itemData[ite] = opt[ite](v): itemData[ite] = v;
                });
                html += renderTemplate(template, itemData);
            });
        }
        $(ele+" tbody").html(html);
    }

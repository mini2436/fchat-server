let api = [];
api.push({
    alias: 'SystemApi',
    order: '1',
    link: '系统api',
    desc: '系统API',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '上传文件',
});
api[0].list.push({
    order: '2',
    desc: '根据文件的Fid获取文件的访问连接',
});
api.push({
    alias: 'TestApi',
    order: '2',
    link: '测试api',
    desc: '测试API',
    list: []
})
api[1].list.push({
    order: '1',
    desc: 'Redis测试接口',
});
api[1].list.push({
    order: '2',
    desc: 'Mysql异步测试接口',
});
api[1].list.push({
    order: '3',
    desc: '根据id查询数据库中的测试数据',
});
api[1].list.push({
    order: '4',
    desc: '获取系统中所有的测试数据',
});
api[1].list.push({
    order: '5',
    desc: '添加mongoDB文档数据',
});
api.push({
    alias: 'UserApi',
    order: '3',
    link: '用户api提供用户数据的相关操作',
    desc: '用户API提供用户数据的相关操作',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '注册用户接口',
});
api.push({
    alias: 'dict',
    order: '4',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    let doc;
    if (apiData.length > 0) {
        for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="#_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}
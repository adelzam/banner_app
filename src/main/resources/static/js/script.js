(function($){
    $(function() {

        $('span.jQtooltip').each(function() {
            var el = $(this);
            var title = el.attr('title');
            if (title && title != '') {
                el.attr('title', '').append('<div>' + title + '</div>');
                var width = el.find('div').width()+30;
                var height = el.find('div').height()+30;
                el.hover(
                    function() {
                        el.find('div')
                            .clearQueue()
                            .delay(200)
                            .animate({width: width + 10, height: height + 10}, 200).show(200)
                            .animate({width: width, height: height}, 200);
                    },
                    function() {
                        el.find('div')
                            .animate({width: width + 10, height: height + 10}, 150)
                            .animate({width: 'hide', height: 'hide'}, 150);
                    }
                ).mouseleave(function() {
                    if (el.children().is(':hidden')) el.find('div').clearQueue();
                });
            }
        })

    })
})(jQuery);
(function () {
    'use strict';

    if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
        var msViewportStyle = document.createElement('style');
        msViewportStyle.appendChild(
            document.createTextNode(
                '@-ms-viewport{width:auto!important}'
            )
        );
        document.head.appendChild(msViewportStyle)
    }

}());
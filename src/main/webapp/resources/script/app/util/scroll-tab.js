$(function () {
    $.widget("custom.scrollTab", {
        // default options
        options: {
            scroll: true,
            scrollRightValue: 100,
            scrollBarWidths: 40,
            scrollLeftValue: 100,
        },

        _create: function () {
            var scrollBarWidths = this.options.scrollBarWidths;
            var scrollRightValue = (this.options.scrollRightValue) * (-1);
            var scrollLeftValue = (this.options.scrollLeftValue) * (-1);
            var isScroll = this.options.scroll;
            var element = this.element;

            var widthOfList = function () {
                var itemsWidth = 0;
                $('.list li').each(function () {
                    var itemWidth = $(this).outerWidth();
                    itemsWidth += itemWidth;
                });
                return itemsWidth;
            };

            var getLeftPosi = function () {
                return $('.list').position().left;
            };

            var widthOfHidden = function () {
                return (($('.wrapper').outerWidth()) - widthOfList());
            };

            this._arrows = $('<div class=\'scroller scroller-left scroll-disable\'>' +
                '<i class=\'glyphicon glyphicon-chevron-left \'></i>' +
                '</div>' +
                '<div class=\'scroller scroller-right\'>' +
                '<i class=\'glyphicon glyphicon-chevron-right\'></i>' +
                '</div>');

            var hiddenWidth = widthOfHidden();
            if ((isScroll == true)) {
                element.before(this._arrows);
            }


//        	scroll right
            $('.scroller-right').click(function () {
                var leftCurrentValue = parseInt($('.list').css('left'));
                var valueDif = widthOfHidden() - leftCurrentValue;


                $('.scroller-left').removeAttr('disabled');
                $('.scroller-left').removeClass('scroll-disable');

                if (valueDif <= scrollRightValue) {
                    $('.list').animate({left: "+=" + scrollLeftValue + "px"}, 20, function () {

                    });
                } else {
                    $('.scroller-right').attr('disabled', 'disabled');
                    $('.scroller-right').addClass('scroll-disable');
                    $('.list').animate({left: widthOfHidden() + 2 + "px"}, 20, function () {

                    });
                }
            });

//        	scroll left
            $('.scroller-left').click(function () {
                var leftCurrentValue = parseInt($('.list').css('left'));

                $('.scroller-right').removeAttr('disabled');
                $('.scroller-right').removeClass('scroll-disable');


                if ((leftCurrentValue < 0) && (leftCurrentValue < scrollLeftValue)) {
                    $('.list').animate({left: "-=" + scrollRightValue + "px"}, 20, function () {

                    });
                } else {
                    $('.scroller-left').attr('disabled', 'disabled');
                    $('.scroller-left').addClass('scroll-disable');
                    $('.list').animate({left: 0 + "px"}, 20, function () {

                    });
                }


            });

            var reAdjust = function () {
                if (($('.wrapper').outerWidth()) < widthOfList()) {
                    $('.scroller-right').show();
                    $('.scroller-left').show();
                }
                else {
                    $('.scroller-right').hide();
                    $('.scroller-left').hide();
                }

            }

            reAdjust();

            $(window).on('resize', function (e) {
                reAdjust();
            });
        },

    });
});
        
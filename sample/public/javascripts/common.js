/**
 * Created with IntelliJ IDEA.
 * User: ashik.salahudeen
 * Date: 11/27/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */

this.$('.js-loading-bar').modal({
    backdrop: 'static',
    show: false
}).on('shown.bs.modal', function( event ) {

        var $bar = $(event.target).find('.progress-bar'),
            _wait = function() {
                setTimeout(function() {
                    if ( $bar.is(':visible')) {
                        $bar.addClass('animate');
                    } else {
                        console.log('not ready');
                        _wait();
                    }
                }, 0);
            };

        _wait();

});

function showCommonProgressBar(){
    var $modal = $('.js-loading-bar'),
        $bar = $modal.find('.progress-bar');

    $modal.modal('show');
}

function hideCommonProgressBar(){
    var $modal = $('.js-loading-bar'),
    $bar = $modal.find('.progress-bar');

    $modal.modal('hide');
    $bar.removeClass('animate');
}

// jQuery expression for case-insensitive filter
$.extend($.expr[":"],{
    "contains-ci": function(elem, i, match, array){
        return (elem.textContent || elem.innerText || $(elem).text() || "").toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
    }
});


var poDiscussionTab = function () {

    var populateDiscussion = function () {
        $('#tbl_po_discussion tbody').html("");
        for (var row = 0; row < purchaseOrderDiscussions.length; row++) {
            var poDiscussion = purchaseOrderDiscussions[row];
            var htmlRow =
                "<tr id='poDiscussionRow_" + row + "' >" +
                "<td>" + (row + 1) + "</td>" +
                "<td>" + poDiscussion.comment+ "</td>" +
                "<td> <div class='hidden-sm hidden-xs'> " +
                "<button type='button' id='" + row + "' class='btn btn-xs btn-bricky tooltips btnDeleteDiscussion' data-placement='top' data-original-title='Remove' >" +
                "<i class='fa fa-times fa fa-white' ></i></button></div> " +
                "</td>" +
                "</tr>";
            $('#tbl_po_discussion > tbody:last-child').append(htmlRow);
        }
        addDiscussionRow(purchaseOrderDiscussions);
    };

    function addDiscussionRow(purchaseOrderDiscussions) {
        for (var row = 0; row < purchaseOrderDiscussions.length; row++) {
            var poDiscussion = purchaseOrderDiscussions[row];
            var poDiscussionId = poDiscussion.id == null ? '' : poDiscussion.id;
            var poUserId = poDiscussion.userId == null ? '' : poDiscussion.userId;
            var htmlRow =
                "<tr id='tblHdnDiscussionRow_" + row + "' >" +
                "<input id='poDiscussionId_" + row + "' name='discussionDTOs[" + row + "].id' value='" + poDiscussionId + "' type='hidden'>" +
                "<input id='poDiscussionUserId_" + row + "' name='discussionDTOs[" + row + "].userId' value='" + poUserId + "' type='hidden' >" +
                "<input id='poDiscussionComment_" + row + "' name='discussionDTOs[" + row + "].comment' value='" + poDiscussion.comment + "' type='hidden' >" +
                "</tr>";
            $('#tbl_po_discussion > tbody:last-child').append(htmlRow);
        }
    };

    var getDiscussionView = function () {
        var $modal = $('#common-modal');
        //    $('body').modalmanager('loading');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/poDiscussionView';
            $modal.load(url, '', function () {
                $modal.modal();
                DiscussionAddModal.init();
            });
        }, 100);
    };

    var addDiscussion = function () {
        var discussionObj = {};
        discussionObj['id'] = '';
        discussionObj['userId'] = '';
        discussionObj['comment'] = CKEDITOR.instances['poDiscussionComment'].getData();
        console.log(CKEDITOR.instances['poDiscussionComment'].getData())
        purchaseOrderDiscussions.push(discussionObj);
    };

    var deleteDiscussion = function (obj) {
        purchaseOrderDiscussions.splice($(obj).attr("id"), 1);
    };


    return {
        populateDiscussion: function () {
            populateDiscussion();
        },
        getDiscussionView: function () {
            getDiscussionView();
        },
        addDiscussion: function () {
            addDiscussion();
        },
        deleteDiscussion: function (obj) {
            deleteDiscussion(obj);
        },

    }
}();

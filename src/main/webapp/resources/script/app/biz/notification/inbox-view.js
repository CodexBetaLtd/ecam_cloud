/*********************************************************************
 * Notification Inbox
 *********************************************************************/
var InboxNotification = function () {

    // Pipelining function for DataTables. To be used to the `ajax` option of DataTables
    $.fn.dataTable.pipeline = function (opts) {
        var conf = $.extend({
            pages: 5,
            url: '',
            data: null,
            method: 'GET'
        }, opts);


        // Private variables for storing the cache
        var cacheLower = -1;
        var cacheUpper = null;
        var cacheLastRequest = null;
        var cacheLastJson = null;

        return function (request, drawCallback, settings) {
            var ajax = false;
            var requestStart = request.start;
            var drawStart = request.start;
            var requestLength = request.length;
            var requestEnd = requestStart + requestLength;

            if (settings.clearCache) {
                ajax = true;
                settings.clearCache = false;
            }
            else if (cacheLower < 0 || requestStart < cacheLower || requestEnd > cacheUpper) {
                ajax = true;
            }
            else if (JSON.stringify(request.order) !== JSON.stringify(cacheLastRequest.order) ||
                JSON.stringify(request.columns) !== JSON.stringify(cacheLastRequest.columns) ||
                JSON.stringify(request.search) !== JSON.stringify(cacheLastRequest.search)
            )
            {
                ajax = true;
            }

            cacheLastRequest = $.extend(true, {}, request);

            if (ajax) {
                if (requestStart < cacheLower) {
                    requestStart = requestStart - (requestLength * (conf.pages - 1));
                    if (requestStart < 0) {
                        requestStart = 0;
                    }
                }

                cacheLower = requestStart;
                cacheUpper = requestStart + (requestLength * conf.pages);

                request.start = requestStart;
                request.length = requestLength * conf.pages;

                // Provide the same `data` options as DataTables.
                if ($.isFunction(conf.data)) {
                    // As a function it is executed with the data object as an arg
                    // for manipulation. If an object is returned, it is used as the
                    // data object to submit
                    var d = conf.data(request);
                    if (d) {
                        $.extend(request, d);
                    }
                }
                else if ($.isPlainObject(conf.data)) {
                    // As an object, the data given extends the default
                    $.extend(request, conf.data);
                }

                settings.jqXHR = $.ajax({
                    "type": conf.method,
                    "url": conf.url,
                    "data": request,
                    "dataType": "json",
                    "cache": false,
                    "success": function (json) {
                        cacheLastJson = $.extend(true, {}, json);

                        if (cacheLower != drawStart) {
                            json.data.splice(0, drawStart - cacheLower);
                        }
                        if (requestLength >= -1) {
                            json.data.splice(requestLength, json.data.length);
                        }

                        drawCallback(json);
                    }
                });
            }
            else {
                json = $.extend(true, {}, cacheLastJson);
                json.draw = request.draw; // Update the echo for each response
                json.data.splice(0, requestStart - cacheLower);
                json.data.splice(requestLength, json.data.length);

                drawCallback(json);
            }
        }
    };

    // Register an API method that will empty the pipelined data, forcing an Ajax fetch on the next draw (i.e. `table.clearPipeline().draw()`)
    $.fn.dataTable.Api.register('clearPipeline()', function () {
        return this.iterator('table', function (settings) {
            settings.clearCache = true;
        });
    });

    var dtInbox = function () {
        
        var tableId = "tbl_notification_list";
        
        $('#' + tableId).dataTable().fnDestroy();
        
        var oTable = $('#' + tableId).DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": $.fn.dataTable.pipeline({
                url: "../restapi/notification/inbox",
                pages: 5
            }),
            columns: [
                {
                    visible: false,
                    orderable: false,
                    searchable: false,
                    render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                // {data: 'subject', orderable: true},
                {data: "content", visible: false, orderable: true, searchable: true},
                {
                    orderable: true,
                    data: 'subject',
                    searchable: true,
                    render: function (data, info, row, meta) {
                        return getTableRaw(row);
                    }
                }
                ],
            oLanguage: {
                sLengthMenu: "Show _MENU_ Rows",
                sSearch: "",
                oPaginate: {
                    "sPrevious": "&laquo;",
                    "sNext": "&raquo;"
                }
            },
            aaSorting: [
                [1, 'desc']
            ],
            aLengthMenu: [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"] // change per page values here
            ],
            // set the initial value
            iDisplayLength: 10,
            sPaginationType: "full_numbers",
            sPaging: 'pagination'
        });
        
        $('#' + tableId + '_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#' + tableId + '_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#' + tableId + '_wrapper .dataTables_length select').select2();
    };

    var previewNotification = function (id) {
        var $modal = $('#stackable-datatable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../notification/preview?id=' + id;
            $modal.load(url, '', function () {
                InboxNotification.initCHEditor();
                $modal.modal();
            });
        }, 1000);
    };


    var initCHEditor = function () {
        CKEDITOR.replace('contentPreview');
    };

    var getTableRaw = function (dataSet) {
        return "" +
            "<div class='messages-item'> " +
            getNotificationButtonSet(dataSet.id) +
            getNotificationMarkAsStarred(dataSet) +
            "<img alt='' src='/images/user-default.png' class='messages-item-avatar'> " +
            getSender(dataSet) +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
            getTime(dataSet) +
            "<br/>" +
            "<div class='tbl-rw-bdy'>" +
            "<span class='messages-item-subject selected-rw'> <strong> Subject : </strong>  " + dataSet.subject + "</span> " +
            "<br/>" +
            "<span class='messages-item-preview selected-rw'> <strong> Content : </strong>" + getUpdatedContent(dataSet) + "</span> " +
            "</div>" +
            "</li>";
    };

    var getUpdatedContent = function (dataSet) {
        if (dataSet.content.replace(/(<([^>]+)>)/ig, "").length > 75) {
            return dataSet.content.replace(/(<([^>]+)>)/ig, "").substring(0, 75).concat(" . . . . ");
        } else {
            return dataSet.content.replace(/(<([^>]+)>)/ig, "")
        }
    };
    
    var getNotificationMarkAsStarred = function(dataSet){

        if (dataSet.isOpen == "false" || dataSet.isOpen == false) {
            return "<span title='Mark as starred' class='messages-item-star btn-delete'>" +
            "<i class='fa fa-star'></i>" +
            "</span> ";
        }       
        return "<span title='Mark as starred' class='messages-item-star'>" +
        "<i class='fa fa-star'></i>" +
        "</span> ";
    };

    var getSender = function (dataSet) {
        if (dataSet.systemMessage == true || dataSet.systemMessage == "true" || dataSet.systemMessage === 'true') {
            return "<span class='messages-item-from'> <strong> From :  [" + dataSet.senderName + "] </strong></span>";
        } else {
            return "<span class='messages-item-from'> <strong> From : </strong> " + dataSet.senderName + "</span>";
        }
    };

    var getTime = function (dataSet) {
        return "<span class='messages-item-time'> <strong> Time : </strong> [" + moment(dataSet.notificationDateTime).format('YYYY-MM-DD HH:mm') + "] </span>";
    };

    var getNotificationButtonSet = function (id) {
        var modalId = "view-modal_" + id;
        return "<div class='message-time' style='position: relative'>  " +
            "<div id='" + id + "' class='message-time' style='position: absolute; right: 4%;top: 5px'>  " +
            "<span><a class='btn btn-xs btn-warning' data-placement='top' onclick='InboxNotification.previewNotification(" + id + ")' type='button' data-toggle='modal' title='Preview'> <i class='fa fa-laptop'></i> </a> </span>" +
            "<span><a class='btn btn-xs btn-danger' type='button' data-toggle='modal' title='Move to Trash' tabindex='-1' href='#" + modalId + "'> <i class='fa fa-trash-o'></i> </a>  </span>" +
            "<span><button class='btn btn-xs btn-success' onclick='InboxNotification.reply(" + id + ")' type='button' data-toggle='tooltip' title='Reply'> <i class='fa fa-reply'></i> </button>  </span>" +
            "<span><button class='btn btn-xs btn-blue' onclick='InboxNotification.forward(" + id + ")' type='button' data-toggle='tooltip' title='Forward' > <i class='fa fa-forward'></i> </button>  </span>" +
            "</div> " +

            "<div id='" + modalId + "' class='modal fade delete-center-modal' tabindex='-1' data-backdrop='static' data-keyboard='false' style='display: none; '>" +
            "<div class='modal-body'>" +
            "<p>Are you sure, you want to proceed this ?</p>" +
            "</div>" +
            "<div class='modal-footer'>" +
            "<a data-dismiss='modal' class='btn btn-blue btn-sm' > Cancel</a> " +
            "<a href='../notification/trashItem?id=" + id + "' class='btn btn-sm btn-red'> Trash </a>" +
            "</div>" +
            "</div>" +

            "</div> ";
    };


    var replyItem = function (id) {
        location.href = "../notification/replyTo?id=" + id;
    };

    var forwardItem = function (id) {
        location.href = "../notification/forwardTo?id=" + id;
    };





    return {
        initCHEditor: function () {
            initCHEditor();
        },
        initInbox: function () {
            dtInbox();
        },
        previewNotification: function (id) {
            previewNotification(id);
        },
        getTableRaw: function (dataSet) {
            getTableRaw(dataSet);
        },

        reply: function (id) {
            replyItem(id);
        },
        forward: function (id) {
            forwardItem(id);
        }
    };

}();


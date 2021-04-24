const ajaxUrl = "welcome";
let datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "description"
            },
            {
                "data": "author"
            },
            {
                "data": "created"
            },
            {
                "defaultContent": "sharing",
                "orderable": false
            },
            {
                "defaultContent": "update",
                "orderable": false
            },
            {
                "defaultContent": "delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});
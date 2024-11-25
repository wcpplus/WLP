//構造gride
function initGride(tableId, queryLimit) {
	$('#' + tableId + ' tfoot').hide();
	loadRemoteData(tableId, queryLimit);
	$('#' + tableId + ' tfoot' + ' td .lastPage').bind('click', function() {
		Object.assign(queryLimit, {
			page : $(this).attr('title')
		});
		loadRemoteData(tableId, queryLimit);
	});
	$('#' + tableId + ' tfoot' + ' td .nextPage').bind('click', function() {
		Object.assign(queryLimit, {
			page : $(this).attr('title')
		});
		loadRemoteData(tableId, queryLimit);
	});
}
// 加载远程数据
function loadRemoteData(tableId, queryLimit) {
	$('#' + tableId + ' tfoot' + ' td .currentPage').html('loading...');
	var dataLoadUrl = $('#' + tableId).attr('action');
	$.post(dataLoadUrl, queryLimit, function(flag) {
		initDataResult(tableId, queryLimit, flag);
	}, 'json');
}
// 加载远程结果集合
function initDataResult(tableId, queryLimit, flag) {
	if (flag.STATE == 0) {
		var fields = new Array()
		$('#' + tableId + ' thead' + ' th').each(function(i, obj) {
			// 字段类型-DATA,NO,FUNC,CHECKBOX
			// 字段key
			var feild = {
				type : $(obj).attr('class'),
				key : $(obj).attr('data-field'),
				func : $(obj).attr('data-func'),
				style : $(obj).attr('style')
			};
			fields.push(feild);
		});
		$('#' + tableId + ' tfoot tr td').attr('colspan', fields.length);
		$('#' + tableId + ' tfoot').show();
		$('#' + tableId + ' tbody').html('');
		$(flag.result.resultList)
				.each(
						function(i, obj) {
							var lineNum = ((flag.result.currentPage - 1) * flag.result.pageSize)
									+ i + 1;
							$('#' + tableId + ' tbody').append(
									initDataTr(lineNum, obj, fields));
						});

		initPages(tableId, flag.result)

	} else {
		alert(flag.MESSAGE);
	}
}
// 构造分页
function initPages(tableId, result) {
	$('#' + tableId + ' tfoot' + ' td .currentPage').html(
			result.currentPage + "/" + result.totalPage);
	$('#' + tableId + ' tfoot' + ' td .lastPage').attr('title',
			result.currentPage - 1);
	$('#' + tableId + ' tfoot' + ' td .nextPage').attr('title',
			result.currentPage + 1);
	if (result.currentPage > 1) {
		$('#' + tableId + ' tfoot' + ' td .lastPage').show();
	} else {
		$('#' + tableId + ' tfoot' + ' td .lastPage').hide();
	}
	if (result.currentPage < result.totalPage) {
		$('#' + tableId + ' tfoot' + ' td .nextPage').show();
	} else {
		$('#' + tableId + ' tfoot' + ' td .nextPage').hide();
	}
}

// 構造數據行
function initDataTr(lineNum, remoteListUnit, fields) {
	var innerTds;
	$(fields)
			.each(
					function(i, obj) {
						var tdStr = null;
						var style = obj.style;

						if (obj.type == 'DATA') {
							var textVal = (remoteListUnit[obj.key] ? remoteListUnit[obj.key]
									: "");
							var title = textVal;
							if (obj.func) {
								textVal = eval(obj.func)(remoteListUnit,
										textVal);
							}
							tdStr = "<td title='" + title + "' "
									+ (style ? "style='" + style + "'" : "")
									+ " >" + textVal + "</td>"
						}
						if (obj.type == 'NO') {
							tdStr = "<td "
									+ (style ? "style='" + style + "'" : "")
									+ " >" + lineNum + "</td>"
						}
						if (innerTds) {
							innerTds = innerTds + tdStr;
						} else {
							innerTds = tdStr;
						}
					});
	return "<tr>" + innerTds + "</tr>"
}
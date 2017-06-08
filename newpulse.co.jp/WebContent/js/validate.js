/**
 * 
 */
function _clearAllError() {
	$("div.field_error.error_tip").remove();
}

function _error(target, msg) {
	$target = $(target);
	if($target.length > 0) {
		if($($target.filter(":first")).attr("type") == "radio" || $($target.filter(":first")).attr("type") == "checkbox") {
			$target = $target.filter(":first");
			$tip = $target.after('<div class="field_error error_tip" style="">' + msg + '</div>')
			$(target).change(function() {$target.next("div .error_tip").remove();});
		} else {
			$target.next("div .error_tip").remove();
			$tip = $target.after('<div class="field_error error_tip" style="">' + msg + '</div>')
			$(target).blur(function() {$target.next("div .error_tip").remove();});
		}
	}
}
function _validate(target, pattern, msg) {
	$target = $(target);
	if($target.length > 0) {
		if($($target.get(0)).attr("type") == "radio" || $($target.get(0)).attr("type") == "checkbox") {
			$_target = $target.filter(":checked");
			if($_target.length == 0) {
				_error(target, msg);
				return false;
			}
		}
		var value	= $(target).val();
		if($(target).is('[sampleText][sampled]')) {
			value = '';
		}
		if(value.match(pattern)) {
			return true;
		}
		_error(target, msg);
		$(target).focus();
	}
	return false;
}

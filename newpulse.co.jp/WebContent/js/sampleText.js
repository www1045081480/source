function sampleText_init() {
	$sampletext = $('input[type=text][sampleText],textarea[sampleText]');
	$sampletext.each(function() {
		if($(this).val() == '' || $(this).val() == $(this).attr('sampleText')) {
			$(this).val($(this).attr('sampleText')).css('color', '#CCC').attr('sampled', true);
		}
		$(this).focus(function() {
			if($(this).val() == $(this).attr('sampleText')) {
				$(this).val('').css('color', '#333');
			}
		});
		$(this).blur(function() {
			if($(this).val() == '' || $(this).val() == $(this).attr('sampleText')) {
				$(this).val($(this).attr('sampleText')).css('color', '#CCC').attr('sampled', true);
			} else {
				$(this).css('color', '#333').removeAttr('sampled');
			}
		});

	});
}

$(document).ready(function() {
	sampleText_init();
});

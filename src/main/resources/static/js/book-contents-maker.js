var  headers = $('.content-container h2');
var output = $('<div>');
headers.each(function(index) {
	if (this.id === '') {
		this.id = 'id-' + index;
	}
	output.append($('<p>' , {class: "col-15 text-truncate"})
		.append($('<a>',  {href: location.href.split('#')[0] + '#' + this.id})
		.append($('<span>')).text($(this).text()))
	);
});
$(".contents").append(output);
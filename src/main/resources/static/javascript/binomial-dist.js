function binomialDistMouseover(mouseEvent) {
	$("#p-x").text(mouseEvent.offsetX);
	$("#p-y").text(mouseEvent.offsetY);
}

$(document).ready(function() {
	$("#binomial-dist").mousemove(binomialDistMouseover);
});

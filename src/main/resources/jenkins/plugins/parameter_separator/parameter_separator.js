(function() {

	var parent = "div[descriptorId=jenkins.plugins.parameter_separator.ParameterSeparatorDefinition]";

	Behaviour.specify(`${parent} textarea`, "autosize", 0, function (e) {
		e.rows = e.textContent.split("\n").length;
	});

	Behaviour.specify(`${parent} a[previewendpoint=/markupFormatter/previewDescription]`, "preview", 200, function (e) {
		var originalOnclick = e.onclick;
		e.onclick = function () {
			var url = "/descriptorByName/jenkins.plugins.parameter_separator.ParameterSeparatorDefinition/preview";
			var params = ["separatorStyle", "sectionHeaderStyle"].map(function(name) {
				var style = encodeURIComponent(document.getElementsByName(`_.${name}`)[0].getValue());
				return `${name}=${style}`;
			});
			e.setAttribute('previewendpoint', `${url}?${params.join("&")}`);
			originalOnclick();
		};
	});

})();

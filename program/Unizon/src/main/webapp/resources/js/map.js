	function initMap() {
			directionsDisplay = new google.maps.DirectionsRenderer();
			var route_end = new google.maps.LatLng(47.541205, 21.639878);
			var mapOptions = {
				zoom : 14,
				draggable : false,
				scrollwheel : false,
				mapTypeControl : false,
				streetViewControl : false,
				disableDoubleClickZoom : true,
				center : route_end,
				mapTypeId : google.maps.MapTypeId.ROADMAP,
				zoomControl : true
			};

			var styles = [ {
				"stylers" : [ {
					"hue" : "#ff1a00"
				}, {
					"invert_lightness" : true
				}, {
					"saturation" : -100
				}, {
					"lightness" : 33
				}, {
					"gamma" : 0.5
				} ]
			}, {
				"featureType" : "water",
				"elementType" : "geometry",
				"stylers" : [ {
					"color" : "#2D333C"
				} ]
			} ];

			function pinSymbol(color) {
				return {
					path : 'M 0,0 C -2,-20 -10,-22 -10,-30 A 10,10 0 1,1 10,-30 C 10,-22 2,-20 0,0 z M -2,-30 a 2,2 0 1,1 4,0 2,2 0 1,1 -4,0',
					fillColor : color,
					fillOpacity : 1,
					strokeColor : '#000',
					strokeWeight : 1,
					scale : 1
				};
			}

			map = new google.maps.Map(document.getElementById('map'), mapOptions);
			map.setOptions({
				styles : styles
			});
			marker = new google.maps.Marker({
				position : route_end,
				map : map,
				animation : google.maps.Animation.DROP,
				icon : pinSymbol("#2E75B6")
			});

			directionsDisplay.setMap(map);
			centre = map.getCenter();

			function markerZoom() {
				  if (map.getZoom() == 14) {
					  map.setZoom(17);
					  map.setCenter(marker.getPosition());
				  }
				  else {
					  map.setZoom(14);
					  map.setCenter(marker.getPosition());
				  }
			}
			
			marker.addListener('click', markerZoom);
			
			google.maps.event.addDomListener(window, 'resize', function() {
				map.setCenter(centre);
			});

		}
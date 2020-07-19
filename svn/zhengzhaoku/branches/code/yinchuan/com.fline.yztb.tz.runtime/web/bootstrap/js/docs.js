/*
 * Documentation JS script
 */
$(function () {
  var slideToTop = $("<div />");
  slideToTop.html('<i class="fa fa-chevron-up"></i>');
  slideToTop.css({
    position: 'fixed',
    bottom: '20px',
    right: '25px',
    width: '40px',
    height: '40px',
    color: '#eee',
    'font-size': '',
    'line-height': '40px',
    'text-align': 'center',
    'background-color': '#222d32',
    cursor: 'pointer',
    'border-radius': '5px',
    'z-index': '99999',
    opacity: '.7',
    'display': 'none'
  });
  slideToTop.on('mouseenter', function () {
    $(this).css('opacity', '1');
  });
  slideToTop.on('mouseout', function () {
    $(this).css('opacity', '.7');
  });
  $('.wrapper').append(slideToTop);
  $(window).scroll(function () {
    if ($(window).scrollTop() >= 150) {
      if (!$(slideToTop).is(':visible')) {
        $(slideToTop).fadeIn(500);
      }
    } else {
      $(slideToTop).fadeOut(500);
    }
  });
  $(slideToTop).click(function () {
    $("body").animate({
      scrollTop: 0
    }, 500);
  });
  $(".sidebar-menu li:not(.treeview) a").click(function () {
    var $this = $(this);
    var target = $this.attr("href");
    if (typeof target === 'string') {
      $("body").animate({
        scrollTop: ($(target).offset().top) + "px"
      }, 500);
    }
  });
  //Skin switcher
  var current_skin = "skin-blue base_skin-aqua";
  var box_skin = "base_box-area-aqua";
  var dialog_skin = "modal-aqua";
  $('#layout-skins-list [data-skin]').click(function(e) {
    e.preventDefault();
    var skinName = $(this).data('skin');
    var boxSkinName = "base_box-area-aqua";
    var dialogSkinName = "modal-aqua";
    if (skinName == "skin-blue base_skin-aqua") {
    	boxSkinName = "base_box-area-aqua";
    	dialogSkinName = "modal-aqua";
    } else if (skinName == "skin-red base_skin-red") {
    	boxSkinName = "base_box-area-red";
    	dialogSkinName = "modal-red";
    } else if (skinName == "skin-blue base_skin-gray") {
    	boxSkinName = "base_box-area-gray";
    	dialogSkinName = "modal-gray";
    } else if (skinName == "skin-blue base_skin-gray3d") {
    	boxSkinName = "base_box-area-gray3d";
    	dialogSkinName = "modal-gray3d";
    }
	$.cookie('skinName', [skinName,boxSkinName,dialogSkinName], {
		expires: 7
	});
	$.cookie('skinNameOld', [current_skin,box_skin,dialog_skin], {
		expires: 7
	});
    $('body').removeClass(current_skin);
    $('body').addClass(skinName);
    if ($("div[name='boxSkin']").length > 0) {
		$("div[name='boxSkin']").removeClass(box_skin);
		$("div[name='boxSkin']").addClass(boxSkinName);
	}
    if ($(".ui-dialog").length > 0) {
		$(".ui-dialog").removeClass(dialog_skin);
		$(".ui-dialog").addClass(dialogSkinName);
    }
    
    if ($("#mainFrame").length > 0) {
        $(window.frames["mainFrame"].document).find("body").removeClass(current_skin);
        $(window.frames["mainFrame"].document).find("body").addClass(skinName);
        if ($(window.frames["mainFrame"].document).find("div[name='boxSkin']").length > 0) {
        	$(window.frames["mainFrame"].document).find("div[name='boxSkin']").removeClass(box_skin);
        	$(window.frames["mainFrame"].document).find("div[name='boxSkin']").addClass(boxSkinName);
        }
        if ($(window.frames["mainFrame"].document).find(".ui-dialog").length > 0) {
        	$(window.frames["mainFrame"].document).find(".ui-dialog").removeClass(dialog_skin);
        	$(window.frames["mainFrame"].document).find(".ui-dialog").addClass(dialogSkinName);
        }
    }
    
    current_skin = skinName;
    box_skin = boxSkinName;
    dialog_skin = dialogSkinName;
  });
  
  var template_name = "fixed";
  $('#layout-template-list [data-template]').click(function(e) {
	    var templateName = $(this).data('template');
	    $('body').removeClass(template_name);
	    $('body').addClass(templateName);
	    
	    $.cookie('templateName', templateName, {
			expires: 7
		});
	    
	    template_name = templateName;
  });
});

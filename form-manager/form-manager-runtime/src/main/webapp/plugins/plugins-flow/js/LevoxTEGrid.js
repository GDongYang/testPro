// JavaScript Document

(function ($) {
  $.fn.extend({ 
   Grid: function (opt){
	if (!opt) var opt = {};
	var allSelected = this.children().children().children().children("input.allSeleced");
	var tr = this.children().nextAll(".LevoxTEGridBody").children()
	var tblbtn = $("input.tblbtn");
	var input = tr.find("td input");
    var row = this.children().nextAll()
	var tbleTitleBg = opt.tbleTitleBg ? opt.tbleTitleBg : "tbleTitleBg";//表格标题背景CSS
	var DefaultCellBG_Odd = opt.DefaultCellBG_Odd ? opt.DefaultCellBG_Odd : "DefaultCellBG_Odd";//表格奇数行CSS
	var DefaultCellBG_Even = opt.DefaultCellBG_Even ? opt.DefaultCellBG_Even : "DefaultCellBG_Even";//表格偶数行CSS
	var CellSelect = opt.CellSelect ? opt.CellSelect : "CellSelect"; //表格选中CSS
	var CellHover = opt.CellHover ? opt.CellHover : "CellHover";//鼠标放上去CSS
	
	this.children().prevAll(".LevoxTEGridTitle").children().addClass(tbleTitleBg)
	row.children("tr:odd").addClass(DefaultCellBG_Odd)
	row.children("tr:even").addClass(DefaultCellBG_Even)	
	input.attr({checked:false})
	allSelected.click(function(){
	if($(this).prop("checked")){
		      input.prop("checked","checked")
		      tr.addClass(CellSelect);
			  
		     }
			 else{
				  input.removeAttr("checked")
				  tr.removeClass(CellSelect);
				  
				 }
		})
	tr.click(function(){
		$(this).toggleClass(CellSelect);
		$(this).find("input").prop("checked",$(this).hasClass(CellSelect))
	}).hover(function(){
		  $(this).addClass(CellHover)
		},function(){
			$(this).removeClass(CellHover)
		})	
	
	tblbtn.click(function(){
			var text = "您选择了";
		input.each(function(i){
			
			if($(this).prop("checked")){
			
				text += $(this).val()
				
				}
				else{
					
					}
			
			})
		alert(text)
		})
	   }
  });	   
})(jQuery);
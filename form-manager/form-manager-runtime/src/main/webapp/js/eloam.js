var DeviceMain;//主头
		var DeviceAssist;//副头
		var Video;//视频
		
		function plugin()
        {
            return document.getElementById('view1');
        }
	
        function view()
        {
            return document.getElementById('view1');
        }
        	
		function thumb()
        {
            return document.getElementById('thumb1');
        }
		
		function addEvent(obj, name, func)
        {
            if (obj.attachEvent) {
                obj.attachEvent("on"+name, func);
            } else {
                obj.addEventListener(name, func, false); 
            }
        }
		function OpenVideo()
		{	
			var sSubType = document.getElementById('subType'); 								
			var sResolution = document.getElementById('selRes'); 	
			var lDeviceName =  document.getElementById('lab1');
			var sDevice =   document.getElementById('device');
			var dev;
			
			if(sDevice.selectedIndex != -1)
			{
				CloseVideo()
			
				if(sDevice.selectedIndex == plugin().Device_GetIndex(DeviceMain))
				{
					dev = DeviceMain;//选中主头
				}
				else if(sDevice.selectedIndex == plugin().Device_GetIndex(DeviceAssist))
				{
					dev = DeviceAssist;//选中副头
				}
							
		
				var SubtypeName;
				if(sSubType.options.selectedIndex != -1)
				{
					var SubtypeName = sSubType.options[sSubType.options.selectedIndex].text;
					if(SubtypeName == "YUY2")
					{
						SelectType = 1;
					}
					else if(SubtypeName == "MJPG")
					{
						SelectType = 2;
					}
					else if(SubtypeName == "UYVY")
					{
						SelectType = 4;
					}
				}
							
				var nResolution = sResolution.selectedIndex;
				
				Video = plugin().Device_CreateVideo(dev, nResolution, SelectType);
				if (Video)
				{
					view().View_SelectVideo(Video);
					view().View_SetText("打开视频中，请等待...", 0);
						
				}				
			}
		}
		
		function CloseVideo()
		{
			if (Video)
			{
				view().View_SetText("", 0);
				plugin().Video_Release(Video);
				Video = null;
			}		
		}
		
		//切换设备
		function changeDev()
		{
			var sSubType = document.getElementById('subType'); 								
			var sResolution = document.getElementById('selRes'); 	
			var lDeviceName =  document.getElementById('lab1');
			var sDevice =   document.getElementById('device');
			var dev;
			
			if(sDevice.selectedIndex != -1)
			{							
				if(sDevice.selectedIndex == plugin().Device_GetIndex(DeviceMain))
				{
					dev = DeviceMain;//选中主头
				}
				else if(sDevice.selectedIndex == plugin().Device_GetIndex(DeviceAssist))
				{
					dev = DeviceAssist;//选中副头
				}
							
				sSubType.options.length = 0;
				var subType = plugin().Device_GetSubtype(dev);
				if (subType & 1) 
				{
					sSubType.add(new Option("YUY2"));
				}
				if (subType & 2) 
				{
					sSubType.add(new Option("MJPG"));
				}
				if (subType & 4) 
				{
					sSubType.add(new Option("UYVY"));
				}
				sSubType.selectedIndex = 0;
		
				var SubtypeName;
				if(sSubType.options.selectedIndex != -1)
				{
					var SubtypeName = sSubType.options[sSubType.options.selectedIndex].text;
					if(SubtypeName == "YUY2")
					{
						SelectType = 1;
					}
					else if(SubtypeName == "MJPG")
					{
						SelectType = 2;
					}
					else if(SubtypeName == "UYVY")
					{
						SelectType = 4;
					}
				}
							
				var nResolution = plugin().Device_GetResolutionCountEx(dev, SelectType);//根据出图模式获取分辨率
				sResolution.options.length = 0; 
				for(var i = 0; i < nResolution; i++)
				{
					var width = plugin().Device_GetResolutionWidthEx(dev, SelectType, i);
					var heigth = plugin().Device_GetResolutionHeightEx(dev, SelectType, i);
					sResolution.add(new Option(width.toString() + "*" + heigth.toString())); 
				}
				sResolution.selectedIndex = 0;
			}
		}		
		
		//切换出图模式
		function changesubType()
		{	
			var sSubType = document.getElementById('subType'); 								
			var sResolution = document.getElementById('selRes'); 	
			var lDeviceName =  document.getElementById('lab1');
			var sDevice =   document.getElementById('device');
			var dev;
			
			if(sDevice.selectedIndex != -1)
			{
				if(sDevice.selectedIndex == plugin().Device_GetIndex(DeviceMain))
				{
					dev = DeviceMain;//选中主头
				}
				else if(sDevice.selectedIndex == plugin().Device_GetIndex(DeviceAssist))
				{
					dev = DeviceAssist;//选中副头
				}
									
				var SubtypeName;
				if(sSubType.options.selectedIndex != -1)
				{
					var SubtypeName = sSubType.options[sSubType.options.selectedIndex].text;
					if(SubtypeName == "YUY2")
					{
						SelectType = 1;
					}
					else if(SubtypeName == "MJPG")
					{
						SelectType = 2;
					}
					else if(SubtypeName == "UYVY")
					{
						SelectType = 4;
					}
				}
							
				var nResolution = plugin().Device_GetResolutionCountEx(dev, SelectType);//根据出图模式获取分辨率
				sResolution.options.length = 0; 
				for(var i = 0; i < nResolution; i++)
				{
					var width = plugin().Device_GetResolutionWidthEx(dev, SelectType, i);
					var heigth = plugin().Device_GetResolutionHeightEx(dev, SelectType, i);
					sResolution.add(new Option(width.toString() + "*" + heigth.toString())); 
				}
				sResolution.selectedIndex = 0;
			}
		}
	
		function Load()
		{
			//设备接入和丢失
			//type设备类型， 1 表示视频设备， 2 表示音频设备
			//idx设备索引
			//dbt 1 表示设备到达， 2 表示设备丢失
			addEvent(plugin(), 'DevChange', function (type, idx, dbt) 
			{
				if(1 == type)//视频设备
				{
					if(1 == dbt)//设备到达
					{
						var deviceType = plugin().Global_GetEloamType(1, idx);
						if(1 == deviceType)//主摄像头
						{
							if(null == DeviceMain)
							{
								DeviceMain = plugin().Global_CreateDevice(1, idx);										
								if(DeviceMain)
								{
									var sSubType = document.getElementById('subType'); 								
									var sResolution = document.getElementById('selRes'); 	
									var lDeviceName =  document.getElementById('lab1');
									var sDevice =   document.getElementById('device');
									
									sDevice.add(new Option(plugin().Device_GetFriendlyName(DeviceMain)));
									sDevice.selectedIndex = idx;//选中主头
									
									changeDev();
									
									OpenVideo();//是主头自动打开视频
								}
							}
						}
						else if(2 == deviceType || 3 == deviceType)//辅摄像头
						{
							if(null == DeviceAssist)
							{
								DeviceAssist = plugin().Global_CreateDevice(1, idx);										
								if(DeviceAssist)
								{				
									var sSubType = document.getElementById('subType'); 								
									var sResolution = document.getElementById('selRes'); 	
									var lDeviceName =  document.getElementById('lab1');
									var sDevice =   document.getElementById('device');							
									
									sDevice.add(new Option(plugin().Device_GetFriendlyName(DeviceAssist)));																			
								}
							}
						}
					}
					else if(2 == dbt)//设备丢失
					{
						if (DeviceMain) 
						{
							if (plugin().Device_GetIndex(DeviceMain) == idx) 
							{
								CloseVideo();
								plugin().Device_Release(DeviceMain);
								DeviceMain = null;
								
								document.getElementById('device').options.length = 0; 
								document.getElementById('subType').options.length = 0; 
								document.getElementById('selRes').options.length = 0; 
							}
						}
						
						if (DeviceAssist) 
						{
							if (plugin().Device_GetIndex(DeviceAssist) == idx) 
							{
								CloseVideo();
								plugin().Device_Release(DeviceAssist);
								DeviceAssist = null;
								
								document.getElementById('device').options.length = 0; 
								document.getElementById('subType').options.length = 0; 
								document.getElementById('selRes').options.length = 0; 
							}
						}
					}
				}
			});
			
			var title = document.title;
			document.title = title + plugin().version;
		
			view().Global_SetWindowName("view");
			thumb().Global_SetWindowName("thumb");

			plugin().Global_InitDevs();
		}
			
		function Unload()
		{
			if (Video)
			{
				view().View_SetText("", 0);
				plugin().Video_Release(Video);
				Video = null;
			}
			if(DeviceMain)
			{
				plugin().Device_Release(DeviceMain);
				DeviceMain = null;	
			}
			if(DeviceAssist)
			{
				plugin().Device_Release(DeviceAssist);
				DeviceAssist = null;	
			}
			plugin().Global_DeinitDevs();
		}
		
		function Scan()
		{	
			var date = new Date();
			var yy = date.getFullYear().toString();
			var mm = (date.getMonth() + 1).toString();
			var dd = date.getDate().toString();
			var hh = date.getHours().toString();
			var nn = date.getMinutes().toString();
			var ss = date.getSeconds().toString();
			var mi = date.getMilliseconds().toString();
			var Name = "D:\\" + yy + mm + dd + hh + nn + ss + mi + ".jpg";
		
			var img = plugin().Video_CreateImage(Video, 0, view().View_GetObject());
			var bSave = plugin().Image_Save(img, Name, 0);
			if (bSave)
			{
				view().View_PlayCaptureEffect();
				thumb().Thumbnail_Add(Name);
			}
			
			plugin().Image_Release(img);
		}
		
		function uploadImage(){
			if(Video)
	        {
		        var img = plugin().Video_CreateImage(Video, 0, view().View_GetObject());
		        if (img)
		        {
			        var http = plugin().Global_CreateHttp("http://127.0.0.1:8003/yztb/photoAction!importImage.action");
			        if (http)
			        {
			        	var date = new Date();
			    		var yy = date.getFullYear().toString();
			    		var mm = (date.getMonth() + 1).toString();
			    		var dd = date.getDate().toString();
			    		var hh = date.getHours().toString();
			    		var nn = date.getMinutes().toString();
			    		var ss = date.getSeconds().toString();
			    		var mi = date.getMilliseconds().toString();
			    		var pname = yy + mm + dd + hh + nn + ss + mi + ".jpg";
				        var b = plugin().Http_UploadImage(http, img, 2, 0, pname);
				        if (b)
				        {
				        	
					        alert("上传成功");
					        $("#pname").val(pname);
				        	$("#fileShow").attr("src","http://127.0.0.1:8003/yztb/photoAction!getImageByName.action?fileFileName="+pname);
				        }
				        else
				        {
					        alert("上传失败");
				        }
				
				        plugin().Http_Release(http);
			        }

			        plugin().Image_Release(img);
		        }
	        }
		}
		
		function test(){
			if(thumb().Thumbnail_HttpUploadCheckImage("http://127.0.0.1:8003/yztb/photoAction!importImage.action",0)){
				var strarr = thumb().Thumbnail_GetHttpServerInfo().split("##");
				for(var i = 0;i<strarr.length;i++){
					alert(strarr[i]);
				}
				var list = new Array();
				for(var i= 0;i<thumb().Thumbnail_GetCount();i++){
					if(thumb().Thumbnail_GetCheck(i)){
						var name = thumb().Thumbnail_GetFileName(i);
						name = name.substring(name.lastIndexOf("\\")+1);
						list.push(name);
					}
				}
				
			}
		}
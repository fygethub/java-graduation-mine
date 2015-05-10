<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" charset="utf-8">
	$(function() {

		$('#layout_east_calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});

		$('#layout_east_onlinePanel').panel({
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
				}
			} ]
		});

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 180px; overflow: hidden;">
		<div id="layout_east_calendar"></div>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<div id="layout_east_onlinePanel" data-options="fit:true,border:false" title="SYPRO收到的捐助">
			<div class="well well-small" style="margin: 3px;">
				<a href="https://me.alipay.com/sypro" target="_blank"><img alt="捐助SyPro" src="${pageContext.request.contextPath}/style/images/alipay.jpg" /></a>
				<hr />
				<div>
					<span class="label label-important">*胜涛</span>(基于SyPro扩展项目)<br /> 2012.08.20 11:38:49(1000元)<br /> 2012.08.20 13:15:35(1000元)<br />2012.12.12 16:35:14(2000元)
				</div>
				<div>
					<span class="label label-success">*杨波</span><br />2013.05.10 14:49:13(180元)
				</div>
				<div>
					<span class="label label-important">*生海</span>(基于SyPro扩展项目)<br />2013.05.12 12:20:34(1000元)
				</div>
				<div>
					<span class="label label-success">*蜀平</span><br />2013.05.12 18:27:21(0.01元)
				</div>
				<div>
					<span class="label label-success">*鹏</span><br />2013.05.14 22:20:48(10元)
				</div>
				<div>
					<span class="label label-success">*阳</span><br />2013.05.14 22:23:46(5元)
				</div>
				<div>
					<span class="label label-success">*雪刚</span><br />2013.05.16 15:17:26(200元)
				</div>
				<div>
					<span class="label label-success">*泽</span><br />2013.05.17 14:24:40(5元)
				</div>
				<div>
					<span class="label label-success">*文强</span><br />2013.05.17 14:44:32(8元)
				</div>
				<div>
					<span class="label label-success">*灿佳</span><br />2013.05.31 20:07:09(2元)
				</div>
				<hr />
				如果发现系统有BUG，请给我发Email:89333367@qq.com
			</div>
		</div>
	</div>
</div>
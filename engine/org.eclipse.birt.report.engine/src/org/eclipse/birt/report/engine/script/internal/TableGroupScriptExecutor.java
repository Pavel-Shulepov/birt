/*******************************************************************************
 * Copyright (c) 2005 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/
package org.eclipse.birt.report.engine.script.internal;

import org.eclipse.birt.report.engine.api.script.element.ITableGroup;
import org.eclipse.birt.report.engine.api.script.eventhandler.ITableGroupEventHandler;
import org.eclipse.birt.report.engine.content.ITableGroupContent;
import org.eclipse.birt.report.engine.executor.ExecutionContext;
import org.eclipse.birt.report.engine.ir.ReportItemDesign;
import org.eclipse.birt.report.engine.script.internal.element.TableGroup;
import org.eclipse.birt.report.engine.script.internal.instance.ReportElementInstance;
import org.eclipse.birt.report.model.api.TableGroupHandle;

public class TableGroupScriptExecutor extends ScriptExecutor
{

	public static void handleOnPrepare( TableGroupHandle groupHandle,
			ExecutionContext context )
	{
		try
		{
			ITableGroup group = new TableGroup( groupHandle );
			ITableGroupEventHandler eh = getEventHandler( groupHandle, context );
			if ( eh != null )
				eh.onPrepare( group, context.getReportContext( ) );
		} catch ( Exception e )
		{
			addException( context, e );
		}
	}
	
	public static void handleOnPageBreak( ITableGroupContent content,
			ExecutionContext context )
	{
		try
		{
			ReportItemDesign tableGroupDesign = ( ReportItemDesign ) content
					.getGenerateBy( );
			ReportElementInstance table = new ReportElementInstance( content, context );
			if ( handleJS( table, tableGroupDesign.getOnPageBreak( ), context ).didRun( ) )
				return;
			ITableGroupEventHandler eh = getEventHandler( tableGroupDesign, context );
			if ( eh != null )
				eh.onPageBreak( table, context.getReportContext( ) );
		} catch ( Exception e )
		{
			addException( context, e );
		}
	}

	private static ITableGroupEventHandler getEventHandler(
			TableGroupHandle handle, ExecutionContext context )
	{
		ITableGroupEventHandler eh = null;
		try
		{
			eh = ( ITableGroupEventHandler ) getInstance( handle, context );
		} catch ( ClassCastException e )
		{
			addClassCastException( context, e, handle.getEventHandlerClass( ),
					ITableGroupEventHandler.class );
		}
		return eh;
	}
	
	private static ITableGroupEventHandler getEventHandler( ReportItemDesign design,
			ExecutionContext context )
	{
		TableGroupHandle handle = ( TableGroupHandle ) design.getHandle( );
		if ( handle == null )
			return null;
		return getEventHandler( handle, context );
	}
}

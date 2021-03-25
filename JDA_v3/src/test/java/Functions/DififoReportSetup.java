///************************************************************************************************************************
//		Author           : SGWS JDA Team 
//		Last Modified by : Anushya Karunakaran
//		Last Modified on : 13-Feb-2020
//		Summary 		 : SQL Validations for SS Classification Rejection scenarios
//
//************************************************************************************************************************/

package Functions;

import org.testng.annotations.Listeners;

import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;

@Listeners(il.co.topq.difido.ReportManagerHook.class)

public abstract class DififoReportSetup {
	
	protected static ReportDispatcher report = ReportManager.getInstance();

}

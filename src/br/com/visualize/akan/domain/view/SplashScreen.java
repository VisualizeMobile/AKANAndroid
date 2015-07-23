/*
 * File: SplashScreen.java Purpose: Provides access to an application's initial
 * presentation.
 */
package br.com.visualize.akan.domain.view;


import org.apache.http.client.ResponseHandler;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import br.com.visualize.akan.R;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.controller.StatisticController;
import br.com.visualize.akan.domain.exception.ConnectionFailedException;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.exception.NullQuotaException;
import br.com.visualize.akan.domain.exception.NullStatisticException;


/**
 * Sets the initial application screen presentation.
 */
public class SplashScreen extends Activity {
    
    public CongressmanController congressmanController;
    public StatisticController statisticController;
    
    private Handler messageHandler = new Handler();
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.splash_screen_activity );
        
        congressmanController = CongressmanController
                .getInstance( getApplicationContext() );
        statisticController = StatisticController
                .getInstance( getApplicationContext() );
        
        requestCongressman();
    }
    
    /**
     * Providence the actions that should be made to the initial request for the
     * operation of the Thread.
     */
    public void requestCongressman() {
        final AlertDialog.Builder msgNeutralBuilder = 
                new AlertDialog.Builder( this )
                .setNeutralButton( "OK", new OkButtonListener() );
        
        msgNeutralBuilder
        .setTitle( "Falha na Conexão" )
        .setMessage( "Verifique sua conexão com a internet." );
        
        final AlertDialog messageFailedConnection = msgNeutralBuilder.create();
        
        msgNeutralBuilder
        .setTitle( "Falha na Recuperação de Dados" )
        .setMessage( "Desculpe. Houve uma falha na recuperação"
        + " dos dados." );
        
        final AlertDialog messageFailedRecoverData = msgNeutralBuilder.create();
        
        msgNeutralBuilder
        .setTitle( "Falha na Inserção de Dados" )
        .setMessage( "Desculpe. Houve uma falha na inserção" 
        + " dos dados." );
        
        final AlertDialog messageFailedInsertData = msgNeutralBuilder.create();
        
        final ProgressDialog progress = new ProgressDialog( this );
        progress.setMessage( "Carregando dados..." );
        progress.show();
        
        new Thread() {
            
            public void run() {
                Looper.prepare();
                
                try {
                    ResponseHandler<String> responseHandler = HttpConnection
                            .getResponseHandler();
                    
                    congressmanController
                    .requestAllCongressman( responseHandler );
                    statisticController.requestStatistics( responseHandler );
                    
                } catch( ConnectionFailedException cfe ) {
                    progress.dismiss();
                    
                    showMessageOnThread( messageFailedConnection,
                            messageHandler );
                } catch( JSONException je ) {
                    progress.dismiss();
                    
                    showMessageOnThread( messageFailedRecoverData,
                            messageHandler );
                } catch( NullCongressmanException nce ) {
                    progress.dismiss();
                    
                    showMessageOnThread( messageFailedInsertData,
                            messageHandler );
                }
                catch( NullQuotaException nce ) {
                    progress.dismiss();
                    
                    showMessageOnThread( messageFailedInsertData,
                            messageHandler );           
                } catch( NullStatisticException nse ) {
                    progress.dismiss();
                    
                    showMessageOnThread( messageFailedInsertData,
                            messageHandler );
                }
                
                runOnUiThread( new Runnable() {
                    
                    @Override
                    public void run() {
                        progress.setMessage( "Dados carregados" );
                        
                        if( !messageFailedConnection.isShowing()
                                && !messageFailedRecoverData.isShowing()
                                && !messageFailedInsertData.isShowing() ) {
                            toListScreen();
                        } else {
                            /* ! Nothing To Do. */
                        }
                        
                        progress.dismiss();
                        Looper.loop();
                    }
                } );
            }
        }.start();
    }
    
    private void showMessageOnThread( final AlertDialog message,
            Handler messageHandler ) {
        
        messageHandler.post( new Runnable() {
            public void run() {
                message.show();
            }
        } );
    }
    
    private void toListScreen() {
        Intent pathListScreen = new Intent( SplashScreen.this, ListScreen.class );
        
        SplashScreen.this.startActivity( pathListScreen );
        SplashScreen.this.finish();
    }
    
    private class OkButtonListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick( DialogInterface dialog, int which ) {
            dialog.dismiss();
            
            Intent myAction = new Intent( SplashScreen.this, ListScreen.class );
            
            SplashScreen.this.startActivity( myAction );
            SplashScreen.this.finish();
        }
    }
}

package br.com.visualize.akan.domain.view;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import br.com.visualize.akan.R;


public class CustomDialog extends Dialog implements OnClickListener {
	Button okButton;
	Context context;
	TextView title = null;
	TextView message = null;
	View view = null;
	
	public CustomDialog( Context context ) {
		super( context );
		this.context = context;
		/**
		 * 'Window.FEATURE_NO_TITLE' - Used to hide the title
		 */
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		// requestWindowFeature(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		setContentView( R.layout.dialog_layout );
		view = getWindow().getDecorView();
		view.setBackgroundResource( android.R.color.transparent );
		
		message = (TextView)findViewById( R.id.dialogMessage );
		
	}
	
	@Override
	public void onClick( DialogInterface dialog, int which ) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setTitle( int titleId ) {
		super.setTitle( titleId );
		this.title.setText( context.getResources().getString( titleId ) );
	}
	
	/**
	 * Set the message text for this dialog's window.
	 * 
	 * @param message
	 */
	public void setMessage( CharSequence message ) {
		this.message.setText( message );
		this.message.setMovementMethod( ScrollingMovementMethod.getInstance() );
		
	}
	
	/**
	 * Set the message text for this dialog's window. The text is retrieved from
	 * the resources with the supplied
	 * 
	 * @param messageId
	 *            - the message text resource identifier
	 */
	
	public void setMessage( int messageId ) {
		this.message
		        .setText( this.context.getResources().getString( messageId ) );
		this.message.setMovementMethod( ScrollingMovementMethod.getInstance() );
	}
}

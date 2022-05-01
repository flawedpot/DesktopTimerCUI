package DesktopTimerCUI.Main;

import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalTime;

public class TimerCount implements Runnable {

	/* フィールド */
	private static LocalTime timerCnt;		/* タイマのカウンタ値 */
	private static Timer timer;				/* 周期処理のためのTimerインスタンス */
	private static boolean timerRunFlg;		/* タイマ動作中フラグ */
	
	/* メソッド */
	/****************************************************/
	/* タイマのカウントダウンを開始するメソッド         */
	/****************************************************/
	public static void timerCountDown()
	{
		/* タイマがすでに動作中？ */
		if( true == TimerCount.timerRunFlg )
		{
			TimerCount.timer.cancel();			/* タイマ停止　*/
			TimerCount.timerRunFlg = false;		/* タイマ動作中フラグ = 停止中 に設定 */
		}
		/* タイマカウンタが00:00:00？ */
		else if( true == TimerCount.timerCnt.equals(LocalTime.of(0, 0, 0)) )
		{
			System.out.println("タイマカウンタに値が設定されていません：" + TimerCount.getTimerCount());
		}
		/* 上記以外 */
		else
		{
			new Thread(new TimerCount()).start();		/* スレッドによってカウントダウンを並列処理で実行 */
		}
	}
	
	/****************************************************/
	/* スレッドによる並列実行させる処理                 */
	/****************************************************/
	@Override
	public void run()
	{
		TimerCount.timer = new Timer();		/* Timerインスタンス生成 */
		
		/* 周期処理する処理 */
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				/* カウント開始時の処理 */
				if( false == TimerCount.timerRunFlg )
				{
					TimerCount.timerRunFlg = true;						/* タイマ動作中フラグ = 動作中 に設定 */
					System.out.println("Time Start!!");
					System.out.println(TimerCount.getTimerCount());		/* タイマカウンタ値を標準出力に表示 */
				}
				/* カウント動作中の処理 */
				else
				{
					TimerCount.timerCnt = TimerCount.timerCnt.minusSeconds(1);		/* タイマカウントダウン */
					System.out.println(TimerCount.getTimerCount());					/* タイマカウンタ値を標準出力に表示 */
					
					/* カウントが00:00:00になったらカウントダウン終了しタイマ停止 */
					if( true == TimerCount.timerCnt.equals(LocalTime.of(0, 0, 0)) )
					{
						TimerCount.timer.cancel();				/* タイマ停止　*/
						TimerCount.timerRunFlg = false;			/* タイマ動作中フラグ = 停止中 に設定 */
						System.out.println("Time Over!!");
					}
				}
			}
		};
		
		/* タスクを1秒周期で実行開始 */
		TimerCount.timer.scheduleAtFixedRate(task,0,1000);
	}

	/****************************************************/
	/* タイマのカウントを停止するメソッド               */
	/****************************************************/
	public static void timerCountStop()
	{
		TimerCount.timer.cancel();			/* タイマ停止　*/
		TimerCount.timerRunFlg = false;		/* タイマ動作中フラグ = 停止中 に設定 */
	}

	/****************************************************/
	/* TimerCountクラスのフィールドを初期化するメソッド */
	/****************************************************/
	public static void timerInit()
	{
		/* タイマが動作中なら停止させる */
		if( true == TimerCount.timerRunFlg )
		{
			TimerCount.timer.cancel();
		}
		
		TimerCount.timerCnt = LocalTime.of(0, 0, 0);		/* タイマカウントに00:00:00を設定 */
		TimerCount.timerRunFlg = false;						/* タイマ動作中フラグ = 停止中 に設定 */
	}

	/****************************************************/
	/* タイマカウンタに値を設定するメソッド             */
	/****************************************************/
	/* コントロール用 */
	public static void setTimerCount(int hms, int updown)
	{
		/* 引数について											*/
		/* hms：		0 = 時間[h], 1 = 分[min], 2 = 秒[sec]		*/
		/* updown：	0 = カウントアップ, 1 = カウントダウン				*/
		
		/* タイマ停止中？ */
		if( false == TimerCount.timerRunFlg )
		{
			/* 引数チェック */
			if( ( hms >= 0 && hms <= 2 ) && ( updown >= 0 && updown <= 1 ) )
			{
				if( 0 == hms)
				{
					if( 0 == updown )
					{
						TimerCount.timerCnt = TimerCount.timerCnt.plusHours(1);
					}
					else
					{
						TimerCount.timerCnt = TimerCount.timerCnt.minusHours(1);
					}
				}
				else if( 1 == hms )
				{
					if( 0 == updown )
					{
						TimerCount.timerCnt = TimerCount.timerCnt.plusMinutes(1);
					}
					else
					{
						TimerCount.timerCnt = TimerCount.timerCnt.minusMinutes(1);
					}
				}
				else
				{
					if( 0 == updown )
					{
						TimerCount.timerCnt = TimerCount.timerCnt.plusSeconds(1);
					}
					else
					{
						TimerCount.timerCnt = TimerCount.timerCnt.minusSeconds(1);
					}
				}
			}
			else
			{
				throw new IllegalArgumentException("hmsは0～1、updownは0～2の値を設定してください。(指定値:hms=" + hms + "、updown=" + updown);
			}
		}
		else
		{
			/* 何もしない */
		}
	}
	
	/* 直接設定するメソッド */
	public static void setTimerCount(int hh, int mm, int ss)
	{
		TimerCount.timerCnt = LocalTime.of(hh, mm, ss);
		System.out.println(TimerCount.getTimerCount());		/* タイマカウンタ値を標準出力に表示 */
	}

	/****************************************************/
	/* タイマカウンタを取得するメソッド                 */
	/****************************************************/
	public static String getTimerCount()
	{
		String ret;		/* 戻り値 */
		
		if( true == TimerCount.timerCnt.equals(LocalTime.of(0, 0, 0)) )
		{
			ret = "00:00:00";
		}
		else
		{
			ret = TimerCount.timerCnt.toString();
		}
		
		return ret;
	}

	/****************************************************/
	/* カウンタの値を文字列(hh:mm:ss)として返すメソッド */
	/****************************************************/
	public String toString()
	{
		return TimerCount.timerCnt.toString();
	}
}

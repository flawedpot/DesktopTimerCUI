package DesktopTimerCUI.Main;

public class Main {

	public static void main(String[] args) {
		
		/* 初期化 */
		boolean loop = true;
		String input = "";
		String[] buf;
		
		TimerCount.timerInit();		/* タイマ初期化 */
		
		while( true == loop )
		{
			System.out.println("入力待ち中...");
			System.out.println("(タイマ設定・・・Aキー　タイマ開始/停止・・・Sキー、終了・・・Eキー)");
			input = new java.util.Scanner(System.in).nextLine();
			
			switch( input )
			{
				case "a":
					System.out.println("タイマ設定値を入力してください(hh:mm:ss)");
					input = new java.util.Scanner(System.in).nextLine();
					buf = input.split(":");
					
					try
					{
						TimerCount.setTimerCount(Integer.parseInt(buf[0]), Integer.parseInt(buf[1]), Integer.parseInt(buf[2]));
						System.out.println("タイマに値を設定しました："+ input.toString());
					}
					catch( NumberFormatException e )
					{
						System.out.println("異常な値が入力されました："+ input.toString());
					}
					break;
					
				case "s":
					TimerCount.timerCountDown();
					break;
				
				case "e":
					loop = false;
					System.out.println("処理を終了します");
					break;
					
				default:
					System.out.println("無効な入力値です：" + input.toString());
					break;
			}
		}
	}
}

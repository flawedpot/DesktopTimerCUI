package DesktopTimerCUI.Main;

public class Main {

	public static void main(String[] args) {
		
		/* ������ */
		boolean loop = true;
		String input = "";
		String[] buf;
		
		TimerCount.timerInit();		/* �^�C�}������ */
		
		while( true == loop )
		{
			System.out.println("���͑҂���...");
			System.out.println("(�^�C�}�ݒ�E�E�EA�L�[�@�^�C�}�J�n/��~�E�E�ES�L�[�A�I���E�E�EE�L�[)");
			input = new java.util.Scanner(System.in).nextLine();
			
			switch( input )
			{
				case "a":
					System.out.println("�^�C�}�ݒ�l����͂��Ă�������(hh:mm:ss)");
					input = new java.util.Scanner(System.in).nextLine();
					buf = input.split(":");
					
					try
					{
						TimerCount.setTimerCount(Integer.parseInt(buf[0]), Integer.parseInt(buf[1]), Integer.parseInt(buf[2]));
						System.out.println("�^�C�}�ɒl��ݒ肵�܂����F"+ buf);
					}
					catch( NumberFormatException e )
					{
						System.out.println("�ُ�Ȓl�����͂���܂����F"+ buf);
					}
					break;
					
				case "s":
					TimerCount.timerCountDown();
					break;
				
				case "e":
					loop = false;
					System.out.println("�������I�����܂�");
					break;
					
				default:
					System.out.println("�����ȓ��͒l�ł��F" + input);
					break;
			}
		}
	}
}

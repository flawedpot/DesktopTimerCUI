package DesktopTimerCUI.Main;

import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalTime;

public class TimerCount implements Runnable {

	/* �t�B�[���h */
	private static LocalTime timerCnt;		/* �^�C�}�̃J�E���^�l */
	private static Timer timer;				/* ���������̂��߂�Timer�C���X�^���X */
	private static boolean timerRunFlg;		/* �^�C�}���쒆�t���O */
	
	/* ���\�b�h */
	/****************************************************/
	/* �^�C�}�̃J�E���g�_�E�����J�n���郁�\�b�h         */
	/****************************************************/
	public static void timerCountDown()
	{
		/* �^�C�}�����łɓ��쒆�H */
		if( true == TimerCount.timerRunFlg )
		{
			TimerCount.timer.cancel();			/* �^�C�}��~�@*/
			TimerCount.timerRunFlg = false;		/* �^�C�}���쒆�t���O = ��~�� �ɐݒ� */
		}
		/* �^�C�}�J�E���^��00:00:00�H */
		else if( true == TimerCount.timerCnt.equals(LocalTime.of(0, 0, 0)) )
		{
			System.out.println("�^�C�}�J�E���^�ɒl���ݒ肳��Ă��܂���F" + TimerCount.getTimerCount());
		}
		/* ��L�ȊO */
		else
		{
			new Thread(new TimerCount()).start();		/* �X���b�h�ɂ���ăJ�E���g�_�E������񏈗��Ŏ��s */
		}
	}
	
	/****************************************************/
	/* �X���b�h�ɂ�������s�����鏈��                 */
	/****************************************************/
	@Override
	public void run()
	{
		TimerCount.timer = new Timer();		/* Timer�C���X�^���X���� */
		
		/* �����������鏈�� */
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				/* �J�E���g�J�n���̏��� */
				if( false == TimerCount.timerRunFlg )
				{
					TimerCount.timerRunFlg = true;						/* �^�C�}���쒆�t���O = ���쒆 �ɐݒ� */
					System.out.println("Time Start!!");
					System.out.println(TimerCount.getTimerCount());		/* �^�C�}�J�E���^�l��W���o�͂ɕ\�� */
				}
				/* �J�E���g���쒆�̏��� */
				else
				{
					TimerCount.timerCnt = TimerCount.timerCnt.minusSeconds(1);		/* �^�C�}�J�E���g�_�E�� */
					System.out.println(TimerCount.getTimerCount());					/* �^�C�}�J�E���^�l��W���o�͂ɕ\�� */
					
					/* �J�E���g��00:00:00�ɂȂ�����J�E���g�_�E���I�����^�C�}��~ */
					if( true == TimerCount.timerCnt.equals(LocalTime.of(0, 0, 0)) )
					{
						TimerCount.timer.cancel();				/* �^�C�}��~�@*/
						TimerCount.timerRunFlg = false;			/* �^�C�}���쒆�t���O = ��~�� �ɐݒ� */
						System.out.println("Time Over!!");
					}
				}
			}
		};
		
		/* �^�X�N��1�b�����Ŏ��s�J�n */
		TimerCount.timer.scheduleAtFixedRate(task,0,1000);
	}

	/****************************************************/
	/* �^�C�}�̃J�E���g���~���郁�\�b�h               */
	/****************************************************/
	public static void timerCountStop()
	{
		TimerCount.timer.cancel();			/* �^�C�}��~�@*/
		TimerCount.timerRunFlg = false;		/* �^�C�}���쒆�t���O = ��~�� �ɐݒ� */
	}

	/****************************************************/
	/* TimerCount�N���X�̃t�B�[���h�����������郁�\�b�h */
	/****************************************************/
	public static void timerInit()
	{
		/* �^�C�}�����쒆�Ȃ��~������ */
		if( true == TimerCount.timerRunFlg )
		{
			TimerCount.timer.cancel();
		}
		
		TimerCount.timerCnt = LocalTime.of(0, 0, 0);		/* �^�C�}�J�E���g��00:00:00��ݒ� */
		TimerCount.timerRunFlg = false;						/* �^�C�}���쒆�t���O = ��~�� �ɐݒ� */
	}

	/****************************************************/
	/* �^�C�}�J�E���^�ɒl��ݒ肷�郁�\�b�h             */
	/****************************************************/
	/* �R���g���[���p */
	public static void setTimerCount(int hms, int updown)
	{
		/* �����ɂ���											*/
		/* hms�F		0 = ����[h], 1 = ��[min], 2 = �b[sec]		*/
		/* updown�F	0 = �J�E���g�A�b�v, 1 = �J�E���g�_�E��				*/
		
		/* �^�C�}��~���H */
		if( false == TimerCount.timerRunFlg )
		{
			/* �����`�F�b�N */
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
				throw new IllegalArgumentException("hms��0�`1�Aupdown��0�`2�̒l��ݒ肵�Ă��������B(�w��l:hms=" + hms + "�Aupdown=" + updown);
			}
		}
		else
		{
			/* �������Ȃ� */
		}
	}
	
	/* ���ڐݒ肷�郁�\�b�h */
	public static void setTimerCount(int hh, int mm, int ss)
	{
		TimerCount.timerCnt = LocalTime.of(hh, mm, ss);
		System.out.println(TimerCount.getTimerCount());		/* �^�C�}�J�E���^�l��W���o�͂ɕ\�� */
	}

	/****************************************************/
	/* �^�C�}�J�E���^���擾���郁�\�b�h                 */
	/****************************************************/
	public static String getTimerCount()
	{
		String ret;		/* �߂�l */
		
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
	/* �J�E���^�̒l�𕶎���(hh:mm:ss)�Ƃ��ĕԂ����\�b�h */
	/****************************************************/
	public String toString()
	{
		return TimerCount.timerCnt.toString();
	}
}

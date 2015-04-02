package Model;

import java.util.Random;

public class ControlNumberFactory 
{
	
	public static String addZeros(int number, int numOfDigits) 
	{
		int tempNum = number;
		int digits = 1;
		for(int i = 0; i < numOfDigits - 1; i++) 
		{
			digits = digits * 10;
		}
		if(tempNum/digits == 0)
		{
			String complete = String.valueOf(tempNum);
			while(tempNum/digits == 0)
			{
				complete = "0" + complete;
				digits /= 10;
			}
			return complete;
		}
		return String.valueOf(tempNum);
	}
	
	public static String generateControlNum(int serviceId) 
	{
		String controlNum = "";
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(99999999) + 1;
		controlNum = addZeros(randomInt, 8);
		String service = addZeros(serviceId, 2);
		
		return service + controlNum;
	}
}

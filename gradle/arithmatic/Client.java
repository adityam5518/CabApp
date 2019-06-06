
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client
{
	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	float num1,num2,ans;
	String symb,ch;
	Scanner sc=new Scanner(System.in);
	public Client() throws Exception
	{
		s=new Socket("localhost",3123);
		dis=new DataInputStream(s.getInputStream());
		dos=new DataOutputStream(s.getOutputStream());
		do
		{
			System.out.println("Enter Equation :");
			num1=Float.parseFloat(sc.nextLine());
			symb=sc.nextLine();
			num2=Float.parseFloat(sc.nextLine());
			dos.writeFloat(num1);
			dos.writeUTF(symb);
			dos.writeFloat(num2);
			ans=dis.readFloat();
			System.out.println("Answer Is : "+ans);
			System.out.println("Do you want to continue? (Y/N)");
			ch=sc.nextLine();
		}
		while(ch.equalsIgnoreCase("y"));
		dos.flush();
		dos.close();
		dis.close();
		s.close();
	}
	public static void main(String[] args)
	{
		try
		{
			new Client();
		}
		catch(Exception e) {System.out.println(e);}
	}
}

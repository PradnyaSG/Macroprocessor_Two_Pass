//The combined code of pass1 and pass2 :
//Keyword parameters and default parameters can also be given as input
import java.io.*;
class KPTAB //for keyword parameter table
{
String arg_name;
String defau;
KPTAB(String an,String d)
{
arg_name=an;
defau=d;
}
}
class mn_table //for macro name table
{
String M_name;
int M_index;
mn_table(String n,int i)
{
M_name=n;
M_index=i;
}
}
class ALA
{
String arg;
String actual;
int key_ac;
ALA(String a)
{
arg=a;
}
}
class Main
{
int mn_table_p=0,ALA_p=0,mdt_p=0,KPTAB_p=0,count_defau=0;
ALA obj1[]=new ALA[30];
mn_table obj2[]=new mn_table[30];
KPTAB obj3[]=new KPTAB[30];
String mdt[]=new String[30];
int srch(String s)
{
for(int i=0;i<ALA_p;i++)
{
if(obj1[i].arg.equals(s))
{
return i;
}
}
return -1;
}
int srch_keypara(String s)
{
for(int i=0;i<ALA_p;i++)
{
String t=(obj1[i].arg).substring(1);
if(t.equals(s))
{
return i;
}
}
return -1;
}
int srch_macro(String s)
{
for(int i=0;i<mn_table_p;i++)
{
if(obj2[i].M_name.equals(s))
{
return obj2[i].M_index;
}
}
return -1;
}
void pass1() throws Exception
{
File it=new File("C:\\Users\\Lenovo\\Desktop\\pass1_input.txt");
FileWriter fw=new FileWriter("C:\\Users\\Lenovo\\Desktop\\pass1_output.txt");
BufferedReader br=new BufferedReader(new FileReader(it));
String st="";
String out_str="";
while((st=br.readLine())!=null)
{
String token[]=st.trim().split(" ");
if(token[0].equals("MACRO"))
{
st=br.readLine();
String token1[]=st.trim().split(" ");
String inst=token1[0];
obj2[mn_table_p]=new mn_table(inst,mdt_p);
for(int i=1;i<token1.length;i++)
{
if(token1[i].contains("="))
{
int ind_eq=token1[i].indexOf("=");
if(ind_eq<(token1[i].length()-1))
{
String f=token1[i].substring(0,ind_eq);
ind_eq+=1;
String g=token1[i].substring(ind_eq);
obj3[KPTAB_p]=new KPTAB(f,g);
obj1[ALA_p]=new ALA(f);
obj1[ALA_p].actual=token1[i].substring(ind_eq);
inst=inst.concat(" #"+ALA_p);
ALA_p++;
KPTAB_p++;
count_defau+=1;
}
else
{
String f=token1[i].substring(0,ind_eq);
obj1[ALA_p]=new ALA(f);
inst=inst.concat(" #"+ALA_p);
ALA_p++;
}
}
else
{
obj1[ALA_p]=new ALA(token1[i]);
inst=inst.concat(" #"+ALA_p);
ALA_p++;
}
}
mn_table_p++;
mdt[mdt_p]=new String(inst);
mdt_p++;
while(!(out_str=br.readLine()).equals("MEND"))
{
token1=out_str.trim().split(" ");
inst="";
for(int i=0;i<token1.length;i++)
{
if(token1[i].charAt(0)=='&')
{
int p=srch(token1[i]);
if(p==-1)
{
System.out.println("error!!!");
}
inst=inst.concat("#"+p+" ");
}
else
{
inst+=token1[i]+" ";
}
}
mdt[mdt_p]=new String(inst);
mdt_p++;
}
mdt[mdt_p]=new String("MEND");
mdt_p++;
}
else
{
fw.write(st+"\n");
}
}
br.close();
fw.close();
}
void print_tables() throws Exception
{
System.out.println("-------Macro Name Table----");
System.out.println("Name Index");
for(int i=0;i<mn_table_p;i++)
{
System.out.println(obj2[i].M_name+" "+obj2[i].M_index);
}
System.out.println("-------Macro Definition Table----");
for(int i=0;i<mdt_p;i++)
{
System.out.println(mdt[i]);
}
System.out.println("-------ALA----");
System.out.println("Formal Actual");
for(int i=0;i<ALA_p;i++)
{
System.out.println(obj1[i].arg+" "+obj1[i].actual);
}
System.out.println("-------KPTAB----");
System.out.println("Argument Default Value");
{
for(int i=0;i<KPTAB_p;i++)
{
System.out.println(obj3[i].arg_name+" "+obj3[i].defau);
}
}
}
void print_ala_kptab()
{
System.out.println("-------ALA----");
System.out.println("Formal Actual");
for(int i=0;i<ALA_p;i++)
{
System.out.println(obj1[i].arg+" "+obj1[i].actual);
}
System.out.println("-------KPTAB----");
System.out.println("Argument Default Value");
{
for(int i=0;i<KPTAB_p;i++)
{
System.out.println(obj3[i].arg_name+" "+obj3[i].defau);
}
}
}
void pass2() throws Exception
{
File it=new File("C:\\Users\\Lenovo\\Desktop\\pass1_output.txt");
FileWriter fw=new FileWriter("C:\\Users\\Lenovo\\Desktop\\pass2_output.txt");
BufferedReader br=new BufferedReader(new FileReader(it));
String st="";
int k;
String arr[];
while((st=br.readLine())!=null)
{
String token[]=st.split(" ");
int j=srch_macro(token[0]);
if(j!=-1)
{
arr=mdt[j].trim().split(" ");
if(token.length<(arr.length-count_defau))//It is ok,if default argument is not given
{
System.out.println("Error due to inaccurate no. of parameters passed");
}
for(int i=1;i<token.length;i++)
{
k=Integer.parseInt(arr[i].substring(1));
if(token[i].contains("="))
{
int ind_eq=token[i].indexOf("=");
String f=token[i].substring(0,ind_eq);
int h=srch_keypara(f);
ind_eq+=1;
String g=token[i].substring(ind_eq);
obj1[h].actual=g;
}
else
{
obj1[k].actual=token[i];
}
}
while(true)
{
j++;
arr=mdt[j].trim().split(" ");
String ans="";
if(!arr[0].equals("MEND"))
{
for(int i=0;i<arr.length;i++)
{
if(arr[i].charAt(0)=='#')
{
k=Integer.parseInt(arr[i].substring(1));
ans+=obj1[k].actual;
ans+=" ";
}
else
{
ans+=arr[i];
ans+=" ";
}
}
fw.write(ans+"\n");
}
else
{
break;
}
}
}//if condition
else
{
fw.write(st+"\n");
}
}//while
br.close();
fw.close();
}
public static void main(String[] args) throws Exception
{
Main obj=new Main();
System.out.println("-------Macro-Processor Pass-1------------");
obj.pass1();
obj.print_tables();
System.out.println("-------Macro-Processor Pass-2------------");
obj.pass2();
obj.print_ala_kptab();
}
}

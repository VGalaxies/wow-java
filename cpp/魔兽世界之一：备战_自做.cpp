#define _CRT_SECURE_NO_WARNINGS
//防止strcpy报错
#include<iomanip>   
#include<iostream>
#include<cstring>
using namespace std;
int cases;
int M;
int set_blood[5];
char set_name[5][10] = { "dragon","ninja","iceman","lion","wolf" };
int order_red[] = { 2,3,4,1,0 };
int order_blue[] = { 3,0,1,2,4 };
class headquarter
{
public:
	int blood;
	int num[5];//武士数量
	int set_time;
	int now_index;//武士降生顺序下标
	int warrior_index;//武士下标
	headquarter(int n)//构造函数
	{
		blood = n;
		for (int i = 0; i < 5; ++i)
			num[i] = 0;
		set_time = 0;
		now_index = 0;
		warrior_index = 0;
	}
};
class warrior_red
{
public:
	char* name = new char[10];//用char name[10]会出现异常
	int blood;
	warrior_red(int index)
	{
		blood = set_blood[order_red[index]];
		strcpy(name, set_name[order_red[index]]);
	}
};
class warrior_blue
{
public:
	char* name = new char[10];//用char name[10]会出现异常
	int blood;
	warrior_blue(int index)
	{
		blood = set_blood[order_blue[index]];
		strcpy(name, set_name[order_blue[index]]);
	}
};
int main()
{
	cin >> cases;
	int num = 1;
	while (num <= cases)
	{
		cin >> M;
		for (int i = 0; i < 5; ++i)
			cin >> set_blood[i];
		int min_blood = 10010;
		for (int i = 0; i < 5; ++i)
			if (set_blood[i] < min_blood)
				min_blood = set_blood[i];
		headquarter red(M);
		headquarter blue(M);
		bool red_stop = false;
		bool blue_stop = false;
		bool red_output = true;
		bool blue_output = true;
		cout << "Case:" << num++ << endl;
		while (!red_stop || !blue_stop)
		{
			if(!red_stop && (red_output||blue_stop))
			{
				if (red.blood >= set_blood[order_red[red.now_index]])
				{
					red_output = false;
					blue_output = true;
					warrior_red tmp(red.now_index);
					red.num[order_red[red.now_index]] += 1;
					red.warrior_index += 1;
					cout<< setfill('0') << setw(3) << red.set_time << " red " << set_name[order_red[red.now_index]] << " "
						<< red.warrior_index << " born with strength " << set_blood[order_red[red.now_index]]
						<< "," << red.num[order_red[red.now_index]] << " " << set_name[order_red[red.now_index]]
						<< " in red headquarter" << endl;
					red.blood -= set_blood[order_red[red.now_index]];
					red.now_index = (red.now_index + 1) % 5;
					red.set_time += 1;
				}
				else if (red.blood < min_blood)
				{
					red_output = false;
					blue_output = true;
					cout << setfill('0') << setw(3) << red.set_time << " red headquarter stops making warriors" << endl;
					red_stop = true;
				}
			}
			if (!blue_stop && (blue_output||red_stop))
			{
				if (blue.blood >= set_blood[order_blue[blue.now_index]])
				{
					red_output = true;
					blue_output = false;
					warrior_blue tmp(blue.now_index);
					blue.num[order_blue[blue.now_index]] += 1;
					blue.warrior_index += 1;
					cout << setfill('0') << setw(3) << blue.set_time << " blue " << set_name[order_blue[blue.now_index]] << " "
						<< blue.warrior_index << " born with strength " << set_blood[order_blue[blue.now_index]]
						<< "," << blue.num[order_blue[blue.now_index]] << " " << set_name[order_blue[blue.now_index]]
						<< " in blue headquarter" << endl;
					blue.blood -= set_blood[order_blue[blue.now_index]];
					blue.now_index = (blue.now_index + 1) % 5;
					blue.set_time += 1;
				}
				else if (blue.blood < min_blood)
				{
					red_output = true;
					blue_output = false;
					cout << setfill('0') << setw(3) << blue.set_time << " blue headquarter stops making warriors" << endl;
					blue_stop = true;
				}
			}
			if (red.blood < set_blood[order_red[red.now_index]] && red.blood >= min_blood)
				red.now_index = (red.now_index + 1) % 5;	
			if (blue.blood < set_blood[order_blue[blue.now_index]] && blue.blood >= min_blood)
				blue.now_index = (blue.now_index + 1) % 5;	
		}
	}
	return 0;
}
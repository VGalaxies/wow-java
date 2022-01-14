#define _CRT_SECURE_NO_WARNINGS
#include<iostream>
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<algorithm>
#include<string>
using namespace std;
int cases;
class headquarter;
class weapon
{
public:
	int index;
	int force;
	int times;
	static const char* name[3];
};
const char* weapon::name[3] = { "sword","bomb","arrow" };

class warrior {
private:
	headquarter* pH;
protected:
	int order;
	int index;
public:
	static int warrior_blood[5];
	static int warrior_force[5];
	static const char warrior_name[5][10];
	warrior(headquarter* p, int _order, int _index) :pH(p), order(_order), index(_index) {}
	virtual void warrior_born(int _time);
	virtual ~warrior() {}

};
int warrior::warrior_blood[5];
int warrior::warrior_force[5];
const char warrior::warrior_name[5][10] = { "dragon" ,"ninja","iceman","lion","wolf" };

class dragon :public warrior {
private:
	weapon wps;
	double morale;
public:
	dragon(headquarter* p, int _order, int _index, int _blood) :warrior(p, _order, _index) {
		wps.index = warrior::index % 3;
		morale = double(_blood) / warrior::warrior_blood[0];
	}
	void warrior_born(int _time);
};
class ninja :public warrior {
private:
	weapon wps[2];
public:
	ninja(headquarter* p, int _order, int _index) :warrior(p, _order, _index) {
		wps[0].index = warrior::index % 3;
		wps[1].index = (warrior::index + 1) % 3;
	}
	void warrior_born(int _time);
};
class iceman :public warrior {
private:
	weapon wps;
public:
	iceman(headquarter* p, int _order, int _index) :warrior(p, _order, _index) {
		wps.index = warrior::index % 3;
	}
	void warrior_born(int _time);
};
class lion :public warrior {
private:
	int loyalty;
public:
	lion(headquarter* p, int _order, int _index, int _blood) :warrior(p, _order, _index) {
		loyalty = _blood;
	}
	void warrior_born(int _time);
};
class wolf :public warrior {
public:
	wolf(headquarter* p, int _order, int _index) :warrior(p, _order, _index) {}
};

class headquarter {
	friend warrior;
private:
	warrior* pW[1000];
	int blood;
	char color[10];
	int warrior_num[5];
	int total_num;
	int index_order;
	int type;
	bool end;
public:
	static int order[2][5];
	void init(int _blood, const char* _color) {
		blood = _blood;
		strcpy(color, _color);
		for (int i = 0; i < 5; ++i)
			warrior_num[i] = 0;
		if (strcmp(color, "red") == 0)
			type = 0;
		else
			type = 1;
		index_order = 0;
		total_num = 0;
		end = false;
	}
	~headquarter() {
		for (int i = 1; i <= total_num; ++i)
			delete pW[i];
	}
	int GetBlood() { return blood; }
	int GetTotalNum() { return total_num; }
	bool GetEnd() { return end; }
	void produce(int _time) {
		if (!end) {
			int count = 0;
			int last_order = order[type][index_order];
			while (blood < warrior::warrior_blood[last_order] && count <= 5) {
				count++;
				index_order = (index_order + 1) % 5;
				last_order = order[type][index_order];
			}
			if (count > 5) {
				end = true;
				printf("%03d %s headquarter stops making warriors\n", _time, color);
			}
			else {
				total_num++;
				warrior_num[last_order]++;
				blood -= pW[total_num]->warrior_blood[last_order];
				switch (last_order)
				{
				case 0:
					pW[total_num] = new dragon(this, last_order, total_num, blood);
					break;
				case 1:
					pW[total_num] = new ninja(this, last_order, total_num);
					break;
				case 2:
					pW[total_num] = new iceman(this, last_order, total_num);
					break;
				case 3:
					pW[total_num] = new lion(this, last_order, total_num, blood);
					break;
				case 4:
					pW[total_num] = new wolf(this, last_order, total_num);
					break;
				}
				pW[total_num]->warrior_born(_time);
				index_order = (index_order + 1) % 5;
			}
		}
	}
};
int headquarter::order[2][5] = { {2,3,4,1,0}, {3,0,1,2,4} };

void warrior::warrior_born(int _time) {
	printf("%03d %s %s %d born with strength %d,%d %s in %s headquarter\n",
		_time, pH->color, warrior_name[order], pH->total_num,
		warrior_blood[order], pH->warrior_num[order],
		warrior_name[order], pH->color);
}
void dragon::warrior_born(int _time) {
	warrior::warrior_born(_time);
	printf("It has a %s,and it's morale is %.2f\n",
		weapon::name[wps.index], morale);
}
void ninja::warrior_born(int _time) {
	warrior::warrior_born(_time);
	printf("It has a %s and a %s\n",
		weapon::name[wps[0].index],
		weapon::name[wps[1].index]);
}
void iceman::warrior_born(int _time) {
	warrior::warrior_born(_time);
	printf("It has a %s\n", weapon::name[wps.index]);
}
void lion::warrior_born(int _time) {
	warrior::warrior_born(_time);
	printf("It's loyalty is %d\n", loyalty);
}

int main() {
	cin >> cases;
	for (int i = 1; i <= cases; ++i) {
		int blood, city, loyalty_decrease;
		cin >> blood >> city >> loyalty_decrease;
		for (int i = 0; i < 5; ++i)
			cin >> warrior::warrior_blood[i];
		for (int i = 0; i < 5; ++i)
			cin >> warrior::warrior_force[i];
		headquarter red;
		red.init(blood, "red");
		headquarter blue;
		blue.init(blood, "blue");
		printf("Case:%d\n", i);
		int time = 0;
		while (!red.GetEnd() || !blue.GetEnd()) {
			red.produce(time);
			blue.produce(time);
			time++;
		}
	}
}
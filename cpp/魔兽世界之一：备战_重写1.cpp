#define _CRT_SECURE_NO_WARNINGS
#include<iostream>
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<algorithm>
#include<string>
using namespace std;
int cases;
int warrior_blood[5];
int order[2][5] = { {2,3,4,1,0}, {3,0,1,2,4} };
const char warrior_name[5][10] = { "dragon" ,"ninja","iceman","lion","wolf" };
class headquarter {
private:
	int blood;
	const char* color;
	int warrior_num[5];
	int time;
	int index_order;
	int type;
	bool end;
public:
	headquarter(int n, const char* s):blood(n), color(s){
		for (int i = 0; i < 5; ++i)
			warrior_num[i] = 0;
		time = 0;
		if (strcmp(color, "red") == 0)
			type = 0;
		else
			type = 1;
		index_order = 0;
		end = false;
	}
	bool GetEnd() {return end;}
	void warrior_born() {
		if (!end) {
			int count = 0;
			while (count <= 5) {
				int last_order = order[type][index_order];
				if (blood < warrior_blood[last_order]) {
					index_order = (index_order + 1) % 5;
					last_order = order[type][index_order];
					count++;
				}
				else {
					blood -= warrior_blood[last_order];
					warrior_num[last_order]++;
					printf("%03d %s %s %d born with strength %d,%d %s in %s headquarter\n",
						time, color, warrior_name[last_order],
						time + 1, warrior_blood[last_order],
						warrior_num[last_order], warrior_name[last_order], color);
					time++;
					index_order = (index_order + 1) % 5;
					break;
				}
			}
			if (count > 5) {
				printf("%03d %s headquarter stops making warriors\n", time, color);
				end = true;
			}
		}
	}
};
int main() {
	cin >> cases;
	for (int i = 1; i <= cases; ++i) {
		int blood;
		cin >> blood;
		for (int i = 0; i < 5; ++i)
			cin >> warrior_blood[i];
		headquarter red(blood,"red");
		headquarter blue(blood, "blue");
		printf("Case:%d\n", i);
		while (!red.GetEnd() || !blue.GetEnd()) {
			red.warrior_born();
			blue.warrior_born();
		}
	}
}
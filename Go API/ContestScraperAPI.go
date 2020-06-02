package main

import "fmt"
import "github.com/PuerkitoBio/goquery"
import "net/http"
import "log"
import "encoding/json"
import "strings"

type Contest struct {
	Code string `json:"Code"`
	Name string `json:"Name"`
	Start string `json:"Start`
	End string `json:"End"`
}

type Profile struct {
	UserName string `json:"UserName"`
	Country string `json:"Country"`
	City string `json:"City"`
	Link string `json:"Link"`
	Role string `json:"Role"`
	Institution string `json:"Institution"`
	CodechefRating string `json:"CodechefRating"`
	GlobalRank string `json:"GlobalRank"`
	CountryRank string `json:"CountryRank"`
}

type Contests []Contest

type JsonContestObject struct{
	Data []Contest 
}

func fetchContest(whichContest int) Contests{
	res, err := http.Get("https://www.codechef.com/contests")
	if err != nil{
		fmt.Println("Error")
	}
	doc, err := goquery.NewDocumentFromReader(res.Body)
	if err != nil{
		fmt.Println("Error")
	}
	allContest := Contests{}
	doc.Find(".dataTable").Each(func(i int, s *goquery.Selection) {
		if whichContest == i{

			switch i{
			case 0:
				fmt.Println("***Ongoing Contest***")
			case 1:
				fmt.Println("***Future Contest***")
			case 2:
				fmt.Println("Past Contest")
			}

			code := "Code"
			name := "Name"
			start := "Start"
			end := "End"
			
			s.Find("tr").Each(func(i int, r *goquery.Selection){
				r.Find("td").Each(func(i int, t *goquery.Selection){
					switch i {
					case 0: 
						code = t.Text()
					case 1:
						name = t.Text()
						name = strings.TrimSpace(name)
					case 2:
						start = t.Text()
					case 3:
						end = t.Text()
					}
				})
				randomContest := Contest{code,name,start,end}
				allContest = append(allContest,randomContest)
				//fmt.Println(code,name,start,end)
			})
		}
	})
	fmt.Println(allContest)
	//randomArray := jsonContestObject{allContest}
	//fmt.Println(randomArray)
	return allContest
}

func fetchProfile() {
	res,err := http.Get("https://www.codechef.com/users/venky_2801")
	if err != nil{
		fmt.Println("Something Went Wrong!")
	}
	doc,err := goquery.NewDocumentFromReader(res.Body)
	if err != nil{
		fmt.Println("Something Went Wrong!")
	}
	//user-details-container plr10
	userDetatilsContainer := doc.Find(".user-details-container").First()
	userDetails := userDetatilsContainer.Find(".user-details").First()
	FullName := userDetails.Find("h2").Text() 
	var stars,username,countryname,state,city,link,profession,institution string
	userDetails.Find("li").Each(func(j int, k *goquery.Selection){
			switch j{
			case 0:
				k.Find("span").Each(func(l int, m *goquery.Selection){
					if l ==0{
						stars = m.Find("span").Text()
					}else{
						username = m.Find("span").Text()
					}
				})

			case 1:
				countryname = k.Find("user-country-name").Text()

			case 2:
				state = k.Find("span").Text()
			
			case 3:
				city = k.Find("span").Text()

			case 4:
				link = k.Find("a").Text()

			case 5:
				profession = k.Find("span").Text()

			case 6:
				institution = k.Find("span").Text()
			}
	})
	fmt.Println(FullName,stars,username,countryname,state,city,link,profession,institution)

}

func allOngoingContest(w http.ResponseWriter, r *http.Request){
	
	onGoingContestArrayList := fetchContest(0)
	ran := JsonContestObject{onGoingContestArrayList}
	b,err := json.Marshal(ran)
	if err != nil{
	fmt.Println("Random ",b)
	}else{
		fmt.Println("Error",string(b))
	}
	json.NewEncoder(w).Encode(ran)
}

func allFutureContest(w http.ResponseWriter, r *http.Request){
	futureContestArrayList := fetchContest(1)
	ran := JsonContestObject{futureContestArrayList}
	b,err := json.Marshal(ran)
	if err != nil{
	fmt.Println("Random ",b)
	}else{
		fmt.Println("Error",string(b))
	}
	json.NewEncoder(w).Encode(ran)
}

func allPastContest(w http.ResponseWriter, r *http.Request){
	pastContestArrayList := fetchContest(2)
	ran := JsonContestObject{pastContestArrayList}
	b,err := json.Marshal(ran)
	if err != nil{
	fmt.Println("Random ",b)
	}else{
		fmt.Println("Error",string(b))
	}
	json.NewEncoder(w).Encode(ran)
}

func homePage(w http.ResponseWriter, r *http.Request){
	fmt.Fprintf(w,"Welcome to Codechef API HomePage \n")
	fmt.Fprintf(w,"To get list of Ongoing Contest go to /ongoing \n")
	fmt.Fprintf(w,"To get list of Future Contest go to /future \n")
	fmt.Fprintf(w,"TO get list of Past Contest go to /past \n")
}

func HandleRequest(){
	fmt.Println("Listening")
	http.HandleFunc("/",homePage)
	http.HandleFunc("/ongoing",allOngoingContest)
	http.HandleFunc("/future",allFutureContest)
	http.HandleFunc("/past",allPastContest)
	log.Fatal(http.ListenAndServe(":80",nil))

}
func main(){
	HandleRequest()
}


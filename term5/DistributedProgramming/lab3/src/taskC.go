package main

import (
	"math/rand"
	"sync"
	"time"
)

func smoking(component int, table *[]bool, SmokingSemaphore chan bool, DealingSemaphore chan bool, waitGroup *sync.WaitGroup) {
	for {
		SmokingSemaphore <- true
		if !(*table)[component] {
			println(component, " is smoking")
			for i := range *table {
				(*table)[i] = false
			}
			DealingSemaphore <- true
		} else {
			<-SmokingSemaphore
			time.Sleep(time.Second)
		}
	}
	waitGroup.Done()
}

func start(table *[]bool, SmokingSemaphore chan bool, DealingSemaphore chan bool, waitGroup *sync.WaitGroup) {
	for {
		<-DealingSemaphore
		var component1, component2 = getComponents()
		println("Dealt", component1+1, "and", component2+1)
		(*table)[component1] = true
		(*table)[component2] = true
		<-SmokingSemaphore
	}
	waitGroup.Done()
}

func getComponents() (int, int) {
	component1 := rand.Intn(3)
	component2 := rand.Intn(3)
	for component1 == component2 {
		component2 = rand.Intn(3)
	}

	return component1, component2
}

func main() {
	var table = make([]bool, 3)
	var waitGroup sync.WaitGroup
	var SmokingSemaphore = make(chan bool)
	var DealingSemaphore = make(chan bool, 1)

	DealingSemaphore <- true
	waitGroup.Add(1)
	go start(&table, SmokingSemaphore, DealingSemaphore, &waitGroup)

	for i := 0; i < 3; i++ {
		waitGroup.Add(1)
		go smoking(i, &table, SmokingSemaphore, DealingSemaphore, &waitGroup)
	}

	waitGroup.Wait()
}

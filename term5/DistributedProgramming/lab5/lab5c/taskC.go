package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var random = rand.New(rand.NewSource(time.Now().UnixNano()))
var MAX = 5

type Arrays struct {
	arrayList [][]int
	sync.WaitGroup
}

func getArrays(n, arraySize int) *Arrays {
	return &Arrays{
		arrayList: initializeArray(n, arraySize),
	}
}
func initializeArray(n, arraySize int) [][]int {
	arrayList := make([][]int, n, arraySize)
	for i := 0; i < n; i++ {
		arrayList[i] = make([]int, arraySize)
		for j := 0; j < arraySize; j++ {
			arrayList[i][j] = random.Intn(MAX)
		}
	}
	return arrayList
}
func printArrays(list *Arrays) {
	for _, i := range list.arrayList {
		fmt.Println(i)
	}
	fmt.Println()
}

func incrOrDecr(arrs *Arrays, group *sync.WaitGroup, index, arraySize int) {
	toChange := random.Intn(arraySize)
	sign := random.Intn(2)
	if sign == 0 {
		if arrs.arrayList[index][toChange] < MAX {
			arrs.arrayList[index][toChange]++
		}
	} else {
		if arrs.arrayList[index][toChange] > (-1)*MAX {
			arrs.arrayList[index][toChange]--
		}
	}

	group.Done()
}

func checkSums(list *Arrays, n int) bool {
	sums := make([]int, n)
	res := true
	for i := 0; i < n; i++ {
		sum := 0
		for _, j := range list.arrayList[i] {
			sum += j
		}
		for k := 0; k < i; k++ {
			if sum != sums[k] {
				res = false
			}
		}
		sums[i] = sum
	}
	fmt.Println(sums)

	return res
}

func changing(list *Arrays, group *sync.WaitGroup, n, arraySize int) {
	stopFlag := false
	for !stopFlag {
		group.Add(n)
		for i := 0; i < n; i++ {
			go incrOrDecr(list, group, i, arraySize)
		}
		group.Wait()
		if checkSums(list, n) {
			stopFlag = true
		}
		printArrays(list)
	}
}

func main() {
	const (
		NUM_OF_ARRAYS = 3
		SIZE_OF_ARRAY = 10
	)

	arr := getArrays(NUM_OF_ARRAYS, SIZE_OF_ARRAY)
	group := new(sync.WaitGroup)
	changing(arr, group, NUM_OF_ARRAYS, SIZE_OF_ARRAY)
}

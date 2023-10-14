package main

import (
	"fmt"
	"math/rand"
	"os"
	"strings"
	"sync"
	"time"
)

func gardener(garden [][]string, m *sync.RWMutex) {
	for {
		m.RLock()
		for i := 0; i < 5; i++ {
			for j := 0; j < 5; j++ {
				if garden[i][j] == "1" {
					garden[i][j] = "0"
				}
			}
		}
		m.RUnlock()
		time.Sleep(2000 * time.Millisecond)
	}
}

func nature(garden [][]string, m *sync.RWMutex) {
	rand.Seed(time.Now().UTC().UnixNano())
	for {
		m.Lock()
		for i := 0; i < 4; i++ {
			garden[rand.Intn(5)][rand.Intn(5)] = "1"
		}
		m.Unlock()
		time.Sleep(1000 * time.Millisecond)
	}
}

func monitor1(garden [][]string, m *sync.RWMutex) {
	file, err := os.Create("output.txt")

	if err != nil {
		fmt.Println("Unable to create file ", err)
		os.Exit(1)
	}
	defer file.Close()

	for {
		m.RLock()
		for i := 0; i < 5; i++ {
			line := strings.Join(garden[i][:], "")
			file.WriteString(line + "\n")
		}
		m.RUnlock()
		file.WriteString("\n\n\n")
		time.Sleep(1000 * time.Millisecond)
	}
}

func monitor2(garden [][]string, m *sync.RWMutex) {
	for {
		m.RLock()
		for _, row := range garden {
			for _, cell := range row {
				print(cell)
			}
			println()
		}
		m.RUnlock()
		println()
		time.Sleep(2000 * time.Millisecond)
	}
}

func main() {
	var garden [][]string
	var waitGroup sync.WaitGroup
	var rwMutex sync.RWMutex

	for j := 0; j < 5; j++ {
		var row []string
		for i := 0; i < 5; i++ {
			row = append(row, "0")
		}
		garden = append(garden, row)
	}

	waitGroup.Add(4)
	go nature(garden, &rwMutex)
	go gardener(garden, &rwMutex)
	go monitor1(garden, &rwMutex)
	go monitor2(garden, &rwMutex)
	waitGroup.Wait()
}

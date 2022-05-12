def load(file):
    board=[]
    with open (file) as f:
        for line in f:
            templist=[]
            for value in line.strip():
                templist.append(value)
            board.append(templist)
    return board

def save(board, file):
    with open (file, "w") as g:
        for list in board:
            for value in list:
                g.write(value)
            g.write("\n")

def display(board):
    print("Current gameboard:")
    for x in range(31):
        print("-", end="")
    print()    
    for row in range(6):
        print(row, "|", end="")
        for column in range(7):
            print(" " + board[row][column] + " |", end="")
        print()
    for x in range(31):
        print("-", end="")
    print()
    print("  ", end="")
    for num in range(7):
        print("  " + str(num), end=" ")
    print()

def move(player, column, board):
    x=5
    fin=False
    row="None"
    while x>=0 and fin==False:
        if board[x][column]==".":
            row=x
            fin=True
        else:
            x-=1
    if row=="None":
        return row
    else:
        board[row][column]=player
        return board

def start():
    howstart=input("New game (N) or load savegame (L)? ")
    if howstart=="N":
        board=[]
        for x in range(6):
            templist=[]
            for x in range(7):
                templist.append(".")
            board.append(templist)
    elif howstart=="L":
        board=load("savegame.txt")
    xcount=0
    ocount=0
    for row in board:
        xcount+=row.count("X")
        ocount+=row.count("O")
    if xcount<=ocount:
        firstplayer="X"
    else:
        firstplayer="O"
    gameloop(board, firstplayer)

def checkhorwin(board):
    for row in range(6):
        for column in range(4):
            list=[]
            for index in range(4):
                list.append(board[row][column+index])
            if list==["X", "X", "X", "X"]:
                return "X"
            elif list==["O", "O", "O", "O"]:
                return "O"
    return "None"

def checkverwin(board):
    for column in range(7):
        for row in range(3):
            list=[]
            for index in range(4):
                list.append(board[row+index][column])
            if list==["X", "X", "X", "X"]:
                return "X"
            elif list==["O", "O", "O", "O"]:
                return "O"
    return "None"

def checkdiawin(board):
    for row in range(3):
        for column in range(4):
            list=[]
            for index in range(4):
                list.append(board[row+index][column+index])
            if list==["X", "X", "X", "X"]:
                return "X"
            elif list==["O", "O", "O", "O"]:
                return "O"
    for row in range(3, 6):
        for column in range(4):
            list=[]
            for index in range(4):
                list.append(board[row-index][column+index])
            if list==["X", "X", "X", "X"]:
                return "X"
            elif list==["O", "O", "O", "O"]:
                return "O"

def checkwin(board):
    if checkhorwin(board)=="X":
        return "X"
    elif checkhorwin(board)=="O":
        return "O"
    elif checkverwin(board)=="X":
        return "X"
    elif checkverwin(board)=="O":
        return "O"
    elif checkdiawin(board)=="X":
        return "X"
    elif checkdiawin(board)=="O":
        return "O"
    else:
        return "None"

def gameloop(board, player):
    response=""
    while response != "Q" and response != "S":
        display(board)
        if checkwin(board)=="X" or checkwin(board)=="O":
            print()
            print("The winner is player "+checkwin(board))
            response="Q"
        else:
            print("Player "+player+" moves next.")
            response=input("Choose your column, or save (S), or quit (Q): ")
            if response=="S":
                save(board, "savegame.txt")
                print("Your game has been saved!")
            elif response=="0" or response=="1" or response=="2" or response=="3" or response=="4" or response=="5" or response=="6":
                moveoutput=move(player, int(response), board)
                if moveoutput=="None":
                    print("Invalid move, try again")
                else:
                    board=moveoutput
                    if player=="X":
                        player="O"
                    elif player=="O":
                        player="X"

start()
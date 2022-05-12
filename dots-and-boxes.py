def load(filename):
    board=[]
    with open (filename) as f:
        for line in f:
            templist=[]
            for value in line.strip("\n"):
                templist.append(value)
            board.append(templist)
    return board

def save(board, filename):
    with open (filename, "w") as f:
        for row in board:
            for value in row:
                f.write(value)
            f.write("\n")

def display(board):
    print("Current gameboard: ")
    print("    ", end="")
    for x in range(len(board)):
        if x%2==1:
            print(str((x+1)//2)+"   ", end="")
    print()
    for row in range(len(board)):
        if row%2==0:
            print("  •", end="")
            for column in range(len(board[row])):
                print(" "+board[row][column]+" •", end="")
            print()
        elif row%2==1:
            print(str((row+1)//2)+" ", end="")
            for column in range(len(board[row])):
                print(board[row][column]+"   ", end="")
            print()

def makemove(board, row, column, player):
    board[row-1][column-1]=player
    return board

def startgame():
    start=str(input("New game (N) or load savegame (L)? "))
    if start=="N":
        size=int(input("Choose a board size (a single positive integer): "))
        board=[]
        for rows in range(2*size+1):
            if rows%2==0:
                row=[]
                for x in range(size):
                    row.append(" ")
                board.append(row)
            elif rows%2==1:
                row=[]
                for x in range(size+1):
                    row.append(" ")
                board.append(row)
        firstplayer="B"
    elif start=="L":
        filename=str(input("Enter the name of the file you want to load: "))
        board=load(filename)
        bcount=0
        rcount=0
        for row in board:
            bcount+=row.count("B")
            rcount+=row.count("R")
        if bcount<=rcount:
            firstplayer="B"
        else:
            firstplayer="R"
    else:
        print("WRONG INPUT")
    game(board, firstplayer, 0, 0)

def addscore(board, row, column, player):
    badd=0
    radd=0
    templist=[]
    templist2=[]
    x=False
    y=False
    if row%2==0:
        if column<len(board)//2+1:
            x=True
            templist.append(board[row-1][column])
            templist.append(board[row-2][column-1])
            templist.append(board[row][column-1])
        if column>1:
            y=True
            templist2.append(board[row-1][column-2])
            templist2.append(board[row-2][column-2])
            templist2.append(board[row][column-2])
    elif row%2==1:
        if row<len(board):
            x=True
            templist.append(board[row][column-1])
            templist.append(board[row][column])
            templist.append(board[row+1][column-1])
        if row>1:
            y=True
            templist2.append(board[row-2][column-1])
            templist2.append(board[row-2][column])
            templist2.append(board[row-3][column-1])
    if templist.count(" ")<=0 and x==True:
        if player=="B":
            badd+=1
        elif player=="R":
            radd+=1
    if templist2.count(" ")<=0 and y==True:
        if player=="B":
            badd+=1
        elif player=="R":
            radd+=1
    return badd, radd

def isfull(board):
    count=0
    for row in board:
        count+=row.count(" ")
    if count<=0:
        return True
    elif count>0:
        return False

def game(board, player, bscore, rscore):
    response=""
    while response!="Q" and response!="S":
        display(board)
        print("The current score is B: "+str(bscore)+" and R: "+str(rscore))
        if isfull(board)==True:
            response="Q"
            if bscore>rscore:
                print("Game Over. The winner is player B.")
            elif rscore>bscore:
                print("Game Over. The winner is player R.")
            elif bscore==rscore:
                print("Game Over. It is a tie.")
        else:
            print("The current player is", player)
            response=input("Enter row to place, enter 'Q' to quit, or enter 'S' to save game: ")
            if response=="S":
                savefile=input("Enter filename to save as: ")
                save(board, savefile)
            elif response!="Q":
                row=int(response)
                column=int(input("Enter column to place: "))
                direction=input("Which direction to place? ('U', 'L', 'D', 'R') ")
                row*=2
                column*=2
                if direction=="U":
                    row-=1
                elif direction=="L":
                    column-=1
                elif direction=="D":
                    row+=1
                elif direction=="R":
                    column+=1
                if row%2==1:
                    column=column//2
                elif row%2==0:
                    column=column//2+1
                if row==0:
                    row=1
                if column==0:
                    column=1
                if board[row-1][column-1]==" ":
                    board=makemove(board, row, column, player)
                    badd, radd=addscore(board, row, column, player)
                    bscore+=badd
                    rscore+=radd
                    if player=="B":
                        player="R"
                    elif player=="R":
                        player="B"
                else:
                    print("Error, that location has already been taken.")  

startgame()

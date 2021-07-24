# game-of-life

<p>
A relatively simple program for running John Conway's <i>Game of Life</i> cellular automaton from the command line.
Comes packaged with many variations on Conway's original ruleset and files for demonstrating many of them.
</p>

<h2>Syntax</h2>
<p>
Usage: GameOfLife input.txt [type] [speed]<br>
input.txt is the file to read the initial state of the playing field from.<br>
[type] is the abbreviated rule variation to use; GOL by default.<br>
[speed] is, if positive, the number of milliseconds to wait between cycles; 200 by default.<br>
If [speed] is negative, its absolute value represents the number of cycles to compute between each console print.<br>
Alternatively, 'GameOfLife help' lists all rule variants.<br>
</p>

<h2>Rule Variants</h2>
<ul>
<li>GOL: Game of Life (default); stay 23, begin 3</li>
<li>HLIF: High Life; stay 23, begin 36</li>
<li>ASIM: Assimilation; stay 4567, begin 345</li>
<li>2X2: 2x2; stay 125, begin 36</li>
<li>DANI: Day and Night; stay 34678, begin 3678</li>
<li>AMOE: Amoeba; stay 1358, begin 357</li>
<li>MOVE: Move; stay 245, begin 368</li>
<li>PGOL: Pseudo Life; stay 238, begin 357</li>
<li>DMOE: Diamoeba; stay 5678, begin 35678</li>
<li>34: 3-4 Life; stay 34, begin 34</li>
<li>LLIF: Long Life; stay 5, begin 345</li>
<li>STAN: Stains; stay 235678, begin 3678</li>
<li>SEED: Seeds; stay , begin 2</li>
<li>MAZE: Maze; stay 12345, begin 3</li>
<li>COAG: Coagulations; stay 235678, begin 378</li>
<li>WALL: Walled Cities; stay 2345, begin 45678</li>
<li>GNAR: Gnarl; stay 1, begin 1</li>
<li>REPL: Replicator; stay 1357, begin 1357</li>
<li>MYST: Mystery; stay 05678, begin 3458</li>
<li>CORL: Coral; stay 45678, begin 3</li>
</ul>
execute if entity @a run scoreboard players add #t probe 1
execute if score #t probe matches 20 run function probe:prepare
execute if score #t probe matches 60 run function probe:build
execute if score #t probe matches 120 run function probe:cam_kitchen
execute if score #t probe matches 280 run function probe:cam_crops
execute if score #t probe matches 440 run function probe:cam_trees
execute if score #t probe matches 600 run function probe:open_stove
execute if score #t probe matches 900 run function probe:open_counter
execute if score #t probe matches 1100 run function probe:open_fridge
execute if score #t probe matches 1300 run function probe:board_cut
execute if score #t probe matches 1500 run function probe:sink_wash
execute if score #t probe matches 1700 run function probe:book
execute if score #t probe matches 2300 run function probe:inventory
execute if score #t probe matches 2700 run function probe:harvest_tree
execute if score #t probe matches 2900 run function probe:end

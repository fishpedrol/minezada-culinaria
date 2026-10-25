fill 0 150 0 31 150 31 minecraft:smooth_stone
fill 0 151 0 31 165 31 minecraft:air
setblock 2 151 2 culinaria:counter[facing=south]
setblock 3 151 2 culinaria:stove[facing=south,lit=true]
setblock 4 151 2 culinaria:oven[facing=south,lit=false]
setblock 5 151 2 culinaria:sink[facing=south,water=8]
setblock 6 151 2 culinaria:cutting_board[facing=south]
setblock 7 151 2 culinaria:mill[facing=south]
setblock 8 151 2 culinaria:blender[facing=south]
setblock 9 151 2 culinaria:fridge[facing=south]
setblock 10 151 2 culinaria:cabinet[facing=south]
setblock 11 151 2 culinaria:pantry[facing=south]
data merge block 6 151 2 {item:{id:"culinaria:tomato",count:1}}
data merge block 7 151 2 {item:{id:"minecraft:wheat",count:8}}
fill 2 150 8 15 150 8 minecraft:farmland[moisture=7]
fill 2 150 9 15 150 9 minecraft:water
fill 2 150 10 9 150 10 minecraft:farmland[moisture=7]
setblock 2 151 8 culinaria:tomato_crop[age=7]
setblock 3 151 8 culinaria:onion_crop[age=7]
setblock 4 151 8 culinaria:garlic_crop[age=7]
setblock 5 151 8 culinaria:lettuce_crop[age=7]
setblock 6 151 8 culinaria:corn_crop[age=7]
setblock 7 151 8 culinaria:rice_crop[age=7]
setblock 8 151 8 culinaria:beans_crop[age=7]
setblock 9 151 8 culinaria:bell_pepper_crop[age=7]
setblock 10 151 8 culinaria:basil_crop[age=7]
setblock 11 151 8 culinaria:parsley_crop[age=7]
setblock 12 151 8 culinaria:strawberry_crop[age=7]
setblock 13 151 8 culinaria:coffee_crop[age=7]
setblock 14 151 8 culinaria:cassava_crop[age=7]
setblock 15 151 8 culinaria:kale_crop[age=7]
setblock 2 151 10 culinaria:tomato_crop[age=0]
setblock 3 151 10 culinaria:tomato_crop[age=1]
setblock 4 151 10 culinaria:tomato_crop[age=2]
setblock 5 151 10 culinaria:tomato_crop[age=3]
setblock 6 151 10 culinaria:tomato_crop[age=4]
setblock 7 151 10 culinaria:tomato_crop[age=5]
setblock 8 151 10 culinaria:tomato_crop[age=6]
setblock 9 151 10 culinaria:tomato_crop[age=7]
fill 0 150 15 31 150 22 minecraft:grass_block
place feature culinaria:apple_tree 3 151 18
place feature culinaria:orange_tree 9 151 18
place feature culinaria:lemon_tree 15 151 18
place feature culinaria:peach_tree 21 151 18
place feature culinaria:cherry_tree 27 151 18
setblock 9 152 21 culinaria:orange_leaves[age=3,fruiting=true,persistent=true]
setblock 10 152 21 culinaria:orange_leaves[age=1,fruiting=true,persistent=true]
setblock 8 152 21 culinaria:orange_leaves[age=2,fruiting=true,persistent=true]

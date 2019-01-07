# TestPlugin

A Simple plugin with some useful commands.

## Command list:

* smite:  
    .description: Places lightning on a target player or when the sender is looking.  
    usage: /smite \[player\]  
    permission: TestPlugin.smite  
    
* kill:  
    description: Kills the user or a target player.  
    usage: /kill \[player\]  
    permission: TestPlugin.kill.self  
    
* tpr:  
    description: Requests a teleport to a specified player  
    usage: /tpr \[player\]  
    permission: TestPlugin.teleport.request  
    
* tpaccept:  
    description: Accepts a teleport request  
    usage: /tpaccept  
    permission: TestPlugin.teleport.accept  
    
* tpdecline:  
    description: Declines a teleport request  
    usage: /tpdecline  
    permission: TestPlugin.teleport.decline  
    
* heal:  
    description: Heals a target player by a given ammount or to max  
    usage: /heal \[amount\] \[player\]  
    permission: TestPlugin.heal  
    
* spawn:  
    description: Move the sender to the spawn  
    usage: /spawn  
    permission: TestPlugin.spawn  
    
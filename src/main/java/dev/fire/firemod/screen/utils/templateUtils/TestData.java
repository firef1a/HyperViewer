package dev.fire.firemod.screen.utils.templateUtils;

public class TestData {
    public static String data = "{\n" +
            "  \"blocks\": [\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"process\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"False\",\n" +
            "                \"tag\": \"Is Hidden\",\n" +
            "                \"action\": \"dynamic\",\n" +
            "                \"block\": \"process\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"data\": \"start game loop\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"set_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"min players\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"num\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"2\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"=\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"repeat\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          \n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"Forever\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"open\",\n" +
            "      \"type\": \"repeat\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"if_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"game in progress\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"txt\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"true\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"=\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"open\",\n" +
            "      \"type\": \"norm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"repeat\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"each\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"player queue list\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"True\",\n" +
            "                \"tag\": \"Allow List Changes\",\n" +
            "                \"action\": \"ForEach\",\n" +
            "                \"block\": \"repeat\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"ForEach\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"open\",\n" +
            "      \"type\": \"repeat\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"select_obj\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"each\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"PlayerName\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"player_action\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"txt\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"Â§cGame already in progress!\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"No spaces\",\n" +
            "                \"tag\": \"Text Value Merging\",\n" +
            "                \"action\": \"ActionBar\",\n" +
            "                \"block\": \"player_action\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"ActionBar\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"select_obj\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          \n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"Reset\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"close\",\n" +
            "      \"type\": \"repeat\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"control\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"Ticks\",\n" +
            "                \"tag\": \"Time Unit\",\n" +
            "                \"action\": \"Wait\",\n" +
            "                \"block\": \"control\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"Wait\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"control\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          \n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"Skip\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"close\",\n" +
            "      \"type\": \"norm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"set_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"queue length\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"player queue list\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"ListLength\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"player_action\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"txt\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"Â§eWaiting for players Â§7| Â§a%var(queue length) / %var(min players)\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"No spaces\",\n" +
            "                \"tag\": \"Text Value Merging\",\n" +
            "                \"action\": \"ActionBar\",\n" +
            "                \"block\": \"player_action\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"ActionBar\",\n" +
            "      \"target\": \"AllPlayers\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"set_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"start game timer\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"num\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"%math(30*20)\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"=\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"repeat\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          \n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"Forever\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"open\",\n" +
            "      \"type\": \"repeat\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"set_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"queue length\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"player queue list\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"ListLength\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"if_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"queue length\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"min players\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \">=\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"open\",\n" +
            "      \"type\": \"norm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"if_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"start game timer\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"num\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"0\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"<=\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"open\",\n" +
            "      \"type\": \"norm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"set_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"game in progress\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"txt\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"true\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"=\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"set_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"ingame player list\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"CreateList\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"set_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"ingame player list\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"player queue list\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"AppendList\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"player_action\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"txt\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"Â§cIngame:Â§r\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"ingame player list\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"txt\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"Â§bIn Queue:Â§r\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 2\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"player queue list\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 3\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"Add spaces\",\n" +
            "                \"tag\": \"Text Value Merging\",\n" +
            "                \"action\": \"SendMessage\",\n" +
            "                \"block\": \"player_action\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 25\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"Regular\",\n" +
            "                \"tag\": \"Alignment Mode\",\n" +
            "                \"action\": \"SendMessage\",\n" +
            "                \"block\": \"player_action\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"SendMessage\",\n" +
            "      \"target\": \"AllPlayers\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"repeat\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"each\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"ingame player list\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"True\",\n" +
            "                \"tag\": \"Allow List Changes\",\n" +
            "                \"action\": \"ForEach\",\n" +
            "                \"block\": \"repeat\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"ForEach\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"open\",\n" +
            "      \"type\": \"repeat\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"set_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"player queue list\",\n" +
            "                \"scope\": \"unsaved\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"each\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"RemoveListValue\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"close\",\n" +
            "      \"type\": \"repeat\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"game_action\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          \n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"ClearScBoard\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"game_action\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          \n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"ShowSidebar\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"game_action\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"txt\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"Â§xÂ§8Â§7Â§bÂ§cÂ§fÂ§fÂ§lTerritories\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"SetScObj\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"start_process\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"Copy\",\n" +
            "                \"tag\": \"Local Variables\",\n" +
            "                \"action\": \"dynamic\",\n" +
            "                \"block\": \"start_process\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 25\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"With no targets\",\n" +
            "                \"tag\": \"Target Mode\",\n" +
            "                \"action\": \"dynamic\",\n" +
            "                \"block\": \"start_process\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"data\": \"game loop\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"control\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          \n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"StopRepeat\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"close\",\n" +
            "      \"type\": \"norm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"set_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"display timer\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"start game timer\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"num\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"20\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 2\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"Default\",\n" +
            "                \"tag\": \"Division Mode\",\n" +
            "                \"action\": \"/\",\n" +
            "                \"block\": \"set_var\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"/\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"set_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"display timer\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"display timer\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"Nearest\",\n" +
            "                \"tag\": \"Round Mode\",\n" +
            "                \"action\": \"RoundNumber\",\n" +
            "                \"block\": \"set_var\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"RoundNumber\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"player_action\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"txt\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"Â§eStarting in: Â§a%var(display timer)\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"No spaces\",\n" +
            "                \"tag\": \"Text Value Merging\",\n" +
            "                \"action\": \"ActionBar\",\n" +
            "                \"block\": \"player_action\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"ActionBar\",\n" +
            "      \"target\": \"AllPlayers\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"set_var\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"var\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"start game timer\",\n" +
            "                \"scope\": \"local\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"num\",\n" +
            "              \"data\": {\n" +
            "                \"name\": \"1\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"-=\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"close\",\n" +
            "      \"type\": \"norm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"else\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"open\",\n" +
            "      \"type\": \"norm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"control\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          \n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"StopRepeat\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"close\",\n" +
            "      \"type\": \"norm\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"control\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"Ticks\",\n" +
            "                \"tag\": \"Time Unit\",\n" +
            "                \"action\": \"Wait\",\n" +
            "                \"block\": \"control\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"Wait\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"close\",\n" +
            "      \"type\": \"repeat\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"block\",\n" +
            "      \"block\": \"control\",\n" +
            "      \"args\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"item\": {\n" +
            "              \"id\": \"bl_tag\",\n" +
            "              \"data\": {\n" +
            "                \"option\": \"Ticks\",\n" +
            "                \"tag\": \"Time Unit\",\n" +
            "                \"action\": \"Wait\",\n" +
            "                \"block\": \"control\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"slot\": 26\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"action\": \"Wait\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"bracket\",\n" +
            "      \"direct\": \"close\",\n" +
            "      \"type\": \"repeat\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
}

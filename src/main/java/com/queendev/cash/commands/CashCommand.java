package com.queendev.cash.commands;

import com.queendev.cash.Cash;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("§cEste comando é exclusivo para jogadores.");
                return false;
            }

            Player p = (Player)sender;
            double cashValue = Cash.getPlugin().manager.getAccountOrCreate(p.getName()).getCash();
            p.sendMessage("§fSeu saldo de cash: §a" + cashValue);
        }

        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("help")) {
                if(!(sender instanceof Player)) {
                    sender.sendMessage("§cEste comando é exclusivo para jogadores.");
                    return false;
                }

                Player p = (Player)sender;
                p.sendMessage("§6Cash §7- §fComandos:");
                p.sendMessage("§1");
                p.sendMessage("§a/cash §7- §fVeja seu saldo de cash.");
                p.sendMessage("§a/cash help §7- §fVeja todos os comandos.");
                if(p.hasPermission("cash.admin")) {
                    p.sendMessage("§a/cash set §7- §fSete o saldo de cash de qualquer jogador.");
                    p.sendMessage("§a/cash remove §7- §fRemova o saldo de cash de qualquer jogador.");
                    p.sendMessage("§a/cash add §7- §fAdicione saldo de cash para qualquer jogador.");
                    p.sendMessage("§a/cash ver §7- §fVeja o saldo de cash de qualquer jogador.");
                }
                return false;
            }

            if(args[0].equalsIgnoreCase("set")) {
                if(sender.hasPermission("cash.admin")) {
                    if(args.length < 3) {
                        sender.sendMessage("§c* Use: /cash set <jogador> [cash]");
                        return false;
                    }

                    String target = String.valueOf(args[1]);
                    Double amount;

                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§c* Digite uma quantida válida!");
                        return false;
                    }

                    Cash.getPlugin().manager.getAccountOrCreate(target).setCash(amount);
                    sender.sendMessage("§fVocê setou o saldo de: §a" + amount + " §fpara o jogador: §a" + target);
                }else {
                    sender.sendMessage("§cVocê não tem permissão para fazer isso!");
                    return false;
                }
                return false;
            }

            if(args[0].equalsIgnoreCase("add")) {
                if(sender.hasPermission("cash.admin")) {
                    if(args.length < 3) {
                        sender.sendMessage("§c* Use: /cash add <jogador> [cash]");
                        return false;
                    }

                    String target = String.valueOf(args[1]);
                    Double oldAmount = Cash.getPlugin().manager.getAccountOrCreate(target).getCash();
                    Double amount;

                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§c* Digite uma quantida válida!");
                        return false;
                    }

                    Cash.getPlugin().manager.getAccountOrCreate(target).setCash(amount + oldAmount);
                    sender.sendMessage("§fVocê adicionou o saldo de: §a" + amount + " §fpara o jogador: §a" + target);
                }else {
                    sender.sendMessage("§cVocê não tem permissão para fazer isso!");
                    return false;
                }
                return false;
            }

            if(args[0].equalsIgnoreCase("remove")) {
                if(sender.hasPermission("cash.admin")) {
                    if(args.length < 3) {
                        sender.sendMessage("§c* Use: /cash remove <jogador> [cash]");
                        return false;
                    }

                    String target = String.valueOf(args[1]);
                    Double oldAmount = Cash.getPlugin().manager.getAccountOrCreate(target).getCash();
                    Double amount;

                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§c* Digite uma quantida válida!");
                        return false;
                    }

                    Cash.getPlugin().manager.getAccountOrCreate(target).setCash(oldAmount - amount);
                    sender.sendMessage("§fVocê removeu o saldo de: §c" + amount + " §fpara o jogador: §a" + target);
                }else {
                    sender.sendMessage("§cVocê não tem permissão para fazer isso!");
                    return false;
                }
                return false;
            }

            if(args[0].equalsIgnoreCase("ver")) {
                if(sender.hasPermission("cash.admin")) {
                    if(args.length < 2) {
                        sender.sendMessage("§c* Use: /cash ver <jogador>");
                        return false;
                    }

                    String target = String.valueOf(args[1]);
                    sender.sendMessage("§fO saldo de: §a" + target + "§fé: §a" + Cash.getPlugin().manager.getAccountOrCreate(target).getCash());
                }else {
                    sender.sendMessage("§cVocê não tem permissão para fazer isso!");
                    return false;
                }
                return false;
            }
        }
        return true;
    }
}

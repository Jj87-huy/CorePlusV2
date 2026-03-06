package me.HaoMC7978.coreplus.commands;

import me.HaoMC7978.coreplus.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SystemCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("coreplus.admin")) {
            sender.sendMessage("§cBạn không có quyền thực hiện lệnh này!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cSử dụng: /system <enable|disable|reload> <id|all>");
            return true;
        }

        String action = args[0].toLowerCase();
        var manager = Main.getInstance().getSystemManager();

        // Xử lý lệnh reload all
        if (action.equals("reload") && args.length > 1 && args[1].equalsIgnoreCase("all")) {
            manager.reloadAll();
            sender.sendMessage("§a[CorePlus] Đã tải lại toàn bộ hệ thống!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cSử dụng: /system " + action + " <id>");
            return true;
        }

        String id = args[1];
        switch (action) {
            case "enable" -> {
                manager.enableSystem(id);
                sender.sendMessage("§a[CorePlus] Đã bật hệ thống: §f" + id);
            }
            case "disable" -> {
                manager.disableSystem(id);
                sender.sendMessage("§c[CorePlus] Đã tắt hệ thống: §f" + id);
            }
            case "reload" -> {
                manager.reloadSystem(id);
                sender.sendMessage("§e[CorePlus] Đã tải lại hệ thống: §f" + id);
            }
            default -> sender.sendMessage("§cHành động không hợp lệ!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("enable", "disable", "reload").stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        if (args.length == 2) {
            List<String> suggestions = new ArrayList<>();
            // Thêm tùy chọn "all" nếu hành động là reload
            if (args[0].equalsIgnoreCase("reload")) {
                suggestions.add("all");
            }
            // Thêm danh sách các ID hiện có
            suggestions.addAll(Main.getInstance().getSystemManager().getRegisteredSystemIds());
            
            return suggestions.stream()
                    .filter(s -> s.startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
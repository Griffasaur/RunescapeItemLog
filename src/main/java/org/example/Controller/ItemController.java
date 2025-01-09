package org.example.Controller;

import org.example.Model.Item;
import org.example.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.util.Elements;

@RestController
@CrossOrigin
@RequestMapping
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController (ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/latest?id={id}")
    public ResponseEntity<Item> getLatestItemPrice(@PathVariable int id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }
}
